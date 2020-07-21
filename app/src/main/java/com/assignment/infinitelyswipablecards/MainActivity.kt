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
import androidx.viewpager.widget.ViewPager
import com.assignment.infinitelyswipablecards.databinding.ActivityMainBinding
import com.assignment.infinitelyswipablecards.models.CardAPIResponsePOJO
import com.assignment.infinitelyswipablecards.network.ApiResponse
import com.assignment.infinitelyswipablecards.network.NetworkCheckerUtility
import com.assignment.infinitelyswipablecards.network.Status
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NetworkCheckerUtility.NetworkAvailableStateListener {

    @Inject
    lateinit var mMainActivityVMFactory: MainActivityVMFactory

    @Inject
    lateinit var gson: Gson

    private var isNetworkAvailable = false
    private var changeImage = true
    private var setPos: Int = 1

    private lateinit var mCardPagerAdapter: CardPagerAdapter

    private lateinit var mMainActivityViewModel: MainActivityViewModel
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private lateinit var mMainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as App).appComponent.doInjection(this)

        mMainActivityViewModel =
            ViewModelProvider(this, mMainActivityVMFactory)[MainActivityViewModel::class.java]

        initialiseProgressDialog()

        val networkCheckerUtility = NetworkCheckerUtility(this)
        networkCheckerUtility.enable(this)


        mMainActivityViewModel.cardsApiResponse.observe(
            this,
            Observer { consumeCardApiResponse(it) })

        hideBothViewPagerAndNoInternetLayout()

        if (isNetworkAvailable)
            mMainActivityViewModel.hitCardsApi()
        else {
            showOnlyNoInternetLayout()
        }

        mMainActivityViewModel.hitCardsApi()

    }

    private fun updateUI(cards: MutableList<CardAPIResponsePOJO.DataBean>) {
        val cardList = mutableListOf<CardAPIResponsePOJO.DataBean>()
        cards.forEach {
            cardList.add(it)
        }

        mCardPagerAdapter = CardPagerAdapter(supportFragmentManager, cardList)

        mMainActivityBinding.viewPager.adapter = mCardPagerAdapter


//        mMainActivityBinding.viewPager.addOnPageChangeListener(object :
//            ViewPager.OnPageChangeListener {
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                when (position) {
//                    cardList.size - 1 -> {
//                        changeImage = true
//                        setPos = 1
//                    }
//                    0 -> {
//                        changeImage = true
//                        setPos = cardList.size - 2
//                    }
//                    else -> {
//                        changeImage = false
//                    }
//                }
//            }
//
//            override fun onPageSelected(position: Int) {
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                if (state == ViewPager.SCROLL_STATE_IDLE && changeImage) {
//                    mMainActivityBinding.viewPager.setCurrentItem(setPos, false)
//                }
//            }
//
//        })

        mMainActivityBinding.viewPager.addOnPageChangeListener(CircularViewPagerHandler(mMainActivityBinding.viewPager))

        mMainActivityBinding.tabDots.setupWithViewPager(mMainActivityBinding.viewPager)

//        mMainActivityBinding.viewPager.currentItem = 1
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

    private fun showProgressDialog() {
        if (!dialog.isShowing)
            dialog.show()
    }

    private fun hideProgressDialog() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

    private fun renderErrorResponse(error: Throwable?) {
        mMainActivityBinding.viewPager.visibility = View.INVISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.VISIBLE
        if (error is HttpException) {
            mMainActivityBinding.tvCheckInternetConn.text = getString(R.string.something_went_wrong)
        } else {
            mMainActivityBinding.tvCheckInternetConn.text = getString(R.string.unknown_error)
        }
        mMainActivityBinding.tvRetry.setOnClickListener {
            mMainActivityViewModel.hitCardsApi()
        }
    }

    private fun renderSuccessResponse(data: ResponseBody) {
        showOnlyViewPagerLayout()
        var jsonString = data.string()
        jsonString = jsonString.replace("/", "")
        Log.d("-------", "-----------------------")
        Log.d("SuccessResp", jsonString)
        Log.d("-------", "-----------------------")

        val cardsData = gson.fromJson(jsonString, CardAPIResponsePOJO::class.java)

        val cards = cardsData.data

        updateUI(cards)
    }

    private fun hideBothViewPagerAndNoInternetLayout() {
        mMainActivityBinding.viewPager.visibility = View.INVISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.INVISIBLE
    }

    private fun showOnlyNoInternetLayout() {
        mMainActivityBinding.viewPager.visibility = View.INVISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.VISIBLE
    }

    private fun showOnlyViewPagerLayout() {
        mMainActivityBinding.viewPager.visibility = View.VISIBLE
        mMainActivityBinding.clNoInternet.visibility = View.INVISIBLE
    }

    override fun networkAvailable() {
        isNetworkAvailable = true
    }

    override fun networkUnavailable() {
        isNetworkAvailable = false
    }
}