package com.assignment.infinitelyswipablecards.network

import com.assignment.infinitelyswipablecards.commons.Constants
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Created by Divya Gupta.
 */

/**
 * A Retrofit interface class which performs the actual API calls
 */
interface ApiCallInterface {
    @GET(Constants.CARDS_DATA_ENDPOINT)
    fun getCardsData(): Single<ResponseBody>
}