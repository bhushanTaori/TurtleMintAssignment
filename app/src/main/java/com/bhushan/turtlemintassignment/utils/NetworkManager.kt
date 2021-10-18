package com.bhushan.turtlemintassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log

class NetworkManager {
    private val stateLock = Any()
    private var networkStateImpl: NetworkStateImpl = NetworkStateImpl(true)
    private lateinit var connectivityMgr: ConnectivityManager
    private val thread = HandlerThread("networkCallback")
    val isConnected: Boolean
        get() {
            synchronized(stateLock) { return networkStateImpl.isConnected() }
        }

    private fun updateNetworkState(isConn: Boolean) {
        val newState = NetworkStateImpl(isConn)
        val connected = isConnected

        synchronized(stateLock) {
            networkStateImpl = newState
        }

        if (newState.isConnected() != connected) {
            Log.d(TAG, "network connectivity changed: $isConnected")
            EventBus.instance.postEvent(Events.EVENT_NETWORK_CONNECTIVITY_CHANGED, isConnected)
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateNetworkState(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateNetworkState(false)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            updateNetworkState(false)
        }
    }

    fun register(context: Context) {
        Log.d(TAG, "Register")
        deRegister()
        thread.start()
        val handler = Handler(thread.looper)
        connectivityMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            connectivityMgr.registerDefaultNetworkCallback(networkCallback, handler)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityMgr.registerDefaultNetworkCallback(networkCallback)
        } else {
            connectivityMgr.unregisterNetworkCallback(networkCallback)
        }
    }

    fun deRegister() {
        Log.d(TAG, "Deregister")
        try {
            connectivityMgr.unregisterNetworkCallback(networkCallback)
            thread.quitSafely()
        } catch (e: Exception) {
            Log.i(TAG, e.localizedMessage?:"Unknown exception")
        }
    }

    class NetworkStateImpl(private val isNetworkConnected: Boolean) : NetworkState {

        override fun isConnected(): Boolean {
            return isNetworkConnected
        }
    }

    companion object {
        private const val TAG = "NetworkManager"
    }
}

interface NetworkState {
    fun isConnected(): Boolean
}