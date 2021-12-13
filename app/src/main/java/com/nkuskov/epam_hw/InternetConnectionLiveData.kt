package com.nkuskov.epam_hw

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import androidx.lifecycle.LiveData
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class InternetConnectionLiveData(private val context: Context) :
    LiveData<Boolean>() {

    private val connectivityManager: ConnectivityManager by lazy { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    private val networksList: MutableList<Network> = ArrayList()

    private val connectionBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null)
                postValue(getInternetStatus(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager))
        }
    }

    override fun onActive() {
        super.onActive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setUpNetworkCallback()
        } else {
            context.registerReceiver(
                connectionBroadcastReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            context.unregisterReceiver(connectionBroadcastReceiver)
    }

    private fun setUpNetworkCallback() {
        connectivityManager.registerNetworkCallback(networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    networksList.add(network)
                    postValue(true)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    postValue(false)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    if (networksList.remove(network) && networksList.count() == 0)
                        postValue(false)
                }
            })
    }

    companion object {
        fun getInternetStatus(connMng: ConnectivityManager): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connMng.activeNetwork ?: return false
                val capabilities =
                    connMng.getNetworkCapabilities(activeNetwork) ?: return false
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } else return connMng.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }

}
