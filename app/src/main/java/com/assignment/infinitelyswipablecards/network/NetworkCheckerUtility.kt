package com.assignment.infinitelyswipablecards.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

/**
 * Created by Divya Gupta.
 */
class NetworkCheckerUtility(val listener: NetworkAvailableStateListener) :
    ConnectivityManager.NetworkCallback() {
    var netRequest: NetworkRequest? = null
    var mNetworkStateListener: NetworkAvailableStateListener? = null

    init {
        netRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
        mNetworkStateListener = listener
    }

    fun enable(context: Context) {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connManager.registerNetworkCallback(netRequest!!, this)
    }

    override fun onAvailable(network: Network) {
        listener.networkAvailable()
    }

    override fun onUnavailable() {
        listener.networkUnavailable()
    }

    interface NetworkAvailableStateListener {
        fun networkAvailable()
        fun networkUnavailable()
    }
}