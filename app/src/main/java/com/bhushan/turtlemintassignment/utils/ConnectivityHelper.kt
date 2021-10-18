package com.bhushan.turtlemintassignment.utils

import android.util.Log
import androidx.lifecycle.LiveData

class ConnectivityHelper(networkManager: NetworkManager) : LiveData<Boolean>(){

    var isConnectedToNetwork = false

    private var networkAvailabilityListener = object : EventInterface {
        override fun publishEvent(eventId: Int, data: Any?) {
            onNetworkChanged(data == true)
        }
    }

    init {
        isConnectedToNetwork = networkManager.isConnected
        registerNetworkCallback()
    }

    private fun onNetworkChanged(isConnected: Boolean) {
        Log.d(TAG, "onNetworkChanged isConnected:${isConnected}")
        isConnectedToNetwork = if (isConnected) {
            true
        } else {
            Log.d(TAG, "onNetworkChanged NOT CONNECTED")
            false
        }
    }

    private fun registerNetworkCallback(){
        EventBus.instance.addEvent(
                Events.EVENT_NETWORK_CONNECTIVITY_CHANGED,
                networkAvailabilityListener
        )
    }

    fun unregisterNetworkCallback() {
        EventBus.instance.removeEvent(Events.EVENT_NETWORK_CONNECTIVITY_CHANGED, networkAvailabilityListener)
    }

    companion object {
        const val TAG = "ConnectivityHelper"
    }
}