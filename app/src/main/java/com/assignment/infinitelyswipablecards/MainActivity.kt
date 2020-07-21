package com.assignment.infinitelyswipablecards

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.assignment.infinitelyswipablecards.models.CardAPIResponsePOJO
import com.assignment.infinitelyswipablecards.models.CardItem
import com.assignment.infinitelyswipablecards.network.ApiResponse
import com.assignment.infinitelyswipablecards.network.Status
import com.google.gson.Gson
import okhttp3.ResponseBody
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mMainActivityVMFactory: MainActivityVMFactory

    @Inject
    lateinit var gson: Gson

    private var isNetworkAvailable = false
    private var changeImage = true
    private var setPos: Int = 1

    private lateinit var mCardPagerAdapter: CardPagerAdapter

    private lateinit var mMainActivityViewModel: MainActivityViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.doInjection(this)

        viewPager = findViewById(R.id.viewPager)

        mMainActivityViewModel =
            ViewModelProvider(this, mMainActivityVMFactory)[MainActivityViewModel::class.java]

        initialiseProgressDialog()

        mMainActivityViewModel.cardsApiResponse.observe(
            this,
            Observer { consumeCardApiResponse(it) })

        mMainActivityViewModel.hitCardsApi()

        mCardPagerAdapter = CardPagerAdapter()
        mCardPagerAdapter.addCardItem(CardItem("4", getString(R.string.lorem_ipsum)))
        mCardPagerAdapter.addCardItem(CardItem("1", getString(R.string.lorem_ipsum)))
        mCardPagerAdapter.addCardItem(CardItem("2", getString(R.string.lorem_ipsum)))
        mCardPagerAdapter.addCardItem(CardItem("3", getString(R.string.lorem_ipsum)))
        mCardPagerAdapter.addCardItem(CardItem("4", getString(R.string.lorem_ipsum)))
        mCardPagerAdapter.addCardItem(CardItem("1", getString(R.string.lorem_ipsum)))

        viewPager.adapter = mCardPagerAdapter
        viewPager.offscreenPageLimit = 3

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when (position) {
                    5 -> {
                        changeImage = true
                        setPos = 1
                    }
                    0 -> {
                        changeImage = true
                        setPos = 4
                    }
                    else -> {
                        changeImage = false
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE && changeImage) {
                    viewPager.setCurrentItem(setPos, false)
                }
            }

        })

        viewPager.currentItem = 1
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
        Toast.makeText(this, "Some Error occured: ${error?.message}", Toast.LENGTH_LONG).show()
    }

    private fun renderSuccessResponse(data: ResponseBody) {
        var jsonString = data.string()
        jsonString = jsonString.replace("/", "")

        val cardsData = gson.fromJson(jsonString, CardAPIResponsePOJO::class.java)
    }
}