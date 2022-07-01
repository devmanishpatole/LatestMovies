package com.devmanishpatole.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Network Utility to detect availability or unavailability of Internet connection
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 01/07/22
 */
@Singleton
class NetworkManager @Inject constructor(@ApplicationContext private val context: Context) :
    ConnectivityManager.NetworkCallback() {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var isConnected = false
        private set

    init {
        with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerDefaultNetworkCallback(this@NetworkManager)
            } else {
                val builder = NetworkRequest.Builder()
                registerNetworkCallback(builder.build(), this@NetworkManager)
            }

            // Retrieve current status of connectivity
            isConnected =
                getNetworkCapabilities(activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false

            networkLiveData.postValue(isConnected)
        }
    }

    /**
     * Returns instance of [LiveData] which can be observed for network changes.
     */
    fun getNetworkLiveData(): LiveData<Boolean> = networkLiveData

    override fun onAvailable(network: Network) {
        isConnected = true
        networkLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        isConnected = false
        networkLiveData.postValue(false)
    }
}