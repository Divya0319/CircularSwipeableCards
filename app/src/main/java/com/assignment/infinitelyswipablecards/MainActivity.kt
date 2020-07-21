package com.assignment.infinitelyswipablecards

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.infinitelyswipablecards.commons.Utils
import com.assignment.infinitelyswipablecards.models.CardAPIResponsePOJO
import com.assignment.infinitelyswipablecards.network.ApiResponse
import com.assignment.infinitelyswipablecards.network.Status
import com.google.gson.Gson
import com.google.gson.JsonElement
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mMainActivityVMFactory: MainActivityVMFactory

    @Inject
    lateinit var gson: Gson

    private var isNetworkAvailable: Boolean = false

    private lateinit var mMainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.doInjection(this)

        mMainActivityViewModel =
            ViewModelProvider(this, mMainActivityVMFactory)[MainActivityViewModel::class.java]

        mMainActivityViewModel.cardsApiResponse.observe(
            this,
            Observer { consumeCardApiResponse(it) })

//        mMainActivityViewModel.hitCardsApi()
    }

    private fun consumeCardApiResponse(apiResponse: ApiResponse?) {
        when (apiResponse?.status) {
            Status.SUCCESS -> {
                hideProgressDialog()
                renderSuccessResponse(apiResponse.data)
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

    private fun showProgressDialog() {

    }

    private fun hideProgressDialog() {

    }

    private fun renderErrorResponse(error: Throwable?) {
        Toast.makeText(this, "Some Error occured: ${error?.message}", Toast.LENGTH_LONG).show()
    }

    private fun renderSuccessResponse(data: JsonElement?) {
        val jsonObject = data?.asJsonObject
        Log.d("--------", "-----------------------")
        Log.d("SuccessResp", Utils.toPrettyFormat(jsonObject.toString()))
        Log.d("--------", "-----------------------")

        val cardsData = gson.fromJson(jsonObject.toString(), CardAPIResponsePOJO::class.java)
    }
}