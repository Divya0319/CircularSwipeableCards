package com.assignment.infinitelyswipablecards.network

import android.content.Context
import com.assignment.infinitelyswipablecards.App
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by Divya Gupta.
 */

/**
 * An intermediate class created which is being used by viewmodels for calling the underlying APICallInterface class methods
 * for making relevant API calls
 */
class Repository(private val apiCallInterface: ApiCallInterface) {

    @Inject
    lateinit var context: Context


    init {
        (App.context as App).appComponent.doInjection(this)

    }

    fun executeCardsDataApiCall(): Single<ResponseBody> {
        return apiCallInterface.getCardsData()
    }

}