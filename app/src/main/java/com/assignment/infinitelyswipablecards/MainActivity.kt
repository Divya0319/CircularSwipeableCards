package com.assignment.infinitelyswipablecards

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.infinitelyswipablecards.adapters.CardPagerAdapter
import com.assignment.infinitelyswipablecards.databinding.ActivityMainBinding
import com.assignment.infinitelyswipablecards.helpers.CircularViewPagerHandler
import com.assignment.infinitelyswipablecards.models.CardAPIResponsePOJO
import com.assignment.infinitelyswipablecards.network.ApiResponse
import com.assignment.infinitelyswipablecards.network.Status
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mMainActivityVMFactory: MainActivityVMFactory

    @Inject
    lateinit var gson: Gson

    private lateinit var mCardPagerAdapter: CardPagerAdapter

    private lateinit var mMainActivityViewModel: MainActivityViewModel
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private lateinit var mMainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)  // DataBinding utilised for accessing views in layout

        (application as App).appComponent.doInjection(this) // dagger componnent is injected, in order to provide dependencies

        mMainActivityViewModel =
            ViewModelProvider(this, mMainActivityVMFactory)[MainActivityViewModel::class.java] // ViewModel instance created using viewmodel factory

        initialiseProgressDialog()

        supportActionBar?.title = "Circular Swipeable Cards"

        mMainActivityViewModel.cardsApiResponse.observe(
            this,
            Observer { consumeCardApiResponse(it) })

        hideBothViewPagerAndNoInternetLayout()

        mMainActivityViewModel.hitCardsApi()


    }

    private fun updateUI(cards: MutableList<CardAPIResponsePOJO.DataBean>) {
        val cardList = mutableListOf<CardAPIResponsePOJO.DataBean>()
        cards.forEach {
            cardList.add(it)
        }

        mCardPagerAdapter =
            CardPagerAdapter(
                supportFragmentManager,
                cardList
            )

        mMainActivityBinding.viewPager.adapter = mCardPagerAdapter

        mMainActivityBinding.viewPager.addOnPageChangeListener(
            CircularViewPagerHandler(
                mMainActivityBinding.viewPager
            )
        )

        mMainActivityBinding.tabDots.setupWithViewPager(mMainActivityBinding.viewPager)

    }

    private fun consumeCardApiResponse(apiResponse: ApiResponse?) {
        when (apiResponse?.status) {
            Status.SUCCESS -> {
                hideProgressDialog()
                apiResponse.data?.let { renderSuccessResponse(it) }
            }

            Status.ERROR -> {
                hideProgressDialog()
                renderErrorResponse(apiResponse.error)
            }

            Status.LOADING ->
                showProgressDialog()

            else -> Log.d("APIStatus", "Unknown")
        }
    }

    private fun initialiseProgressDialog() {
        alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(R.layout.progress_dialog_with_message)
        dialog = alertDialogBuilder.create()
        dialog.setCancelable(false)
    }

    // shows the custom progress dialog
    private fun showProgressDialog() {
        hideBothViewPagerAndNoInternetLayout()
        if (!dialog.isShowing)
            dialog.show()
    }

    // hides the custom progress dialog
    private fun hideProgressDialog() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

    // gets called when api call returned an error
    private fun renderErrorResponse(error: Throwable?) {
        showOnlyNoInternetLayout()
        mMainActivityBinding.tvRetry.setOnClickListener {
            mMainActivityViewModel.hitCardsApi()
        }
        if (error is HttpException) {
            mMainActivityBinding.tvCheckInternetConn.text = getString(R.string.something_went_wrong)
        } else {
            mMainActivityBinding.tvCheckInternetConn.text = getString(R.string.unknown_error)
        }
    }

    // gets called when API response is successfully received
    private fun renderSuccessResponse(data: ResponseBody) {
        showOnlyViewPagerLayout()
        var jsonString = data.string()
        jsonString = jsonString.replace("/", "")

        val cardsData = gson.fromJson(jsonString, CardAPIResponsePOJO::class.java)

        val cards = cardsData.data

        updateUI(cards)
    }

    // hide both viewpager and no internet layout
    private fun hideBothViewPagerAndNoInternetLayout() {
        mMainActivityBinding.viewPager.visibility = View.INVISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.INVISIBLE
    }

    // hide viewpager layout, and show only no internet layout
    private fun showOnlyNoInternetLayout() {
        mMainActivityBinding.viewPager.visibility = View.INVISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.VISIBLE
    }

    // hide no internet layout, and show only viewpager
    private fun showOnlyViewPagerLayout() {
        mMainActivityBinding.viewPager.visibility = View.VISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.INVISIBLE
    }

}