package com.assignment.infinitelyswipablecards.network

import okhttp3.ResponseBody

/**
 * Created by Divya Gupta.
 */

/**
 * A network utility class created for capturing the API call responses,
 * let it be call ongoing, call returned success , call returned error, or call returned empty response with success status code(200)
 */
class ApiResponse(val status: Status, val data: ResponseBody?, val error: Throwable?) {
    companion object {

        // call ongoing
        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null)
        }

        // call returned success
        fun success(data: ResponseBody): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        // call returned error
        fun error(error: Throwable): ApiResponse {
            return ApiResponse(Status.ERROR, null, error)
        }

        // call returned empty response with success status code(200)
        fun complete(): ApiResponse {
            return ApiResponse(Status.COMPLETE, null, null)
        }
    }
}