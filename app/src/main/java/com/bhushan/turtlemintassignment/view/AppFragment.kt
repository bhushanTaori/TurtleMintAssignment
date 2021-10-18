package com.bhushan.turtlemintassignment.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bhushan.turtlemintassignment.utils.EventBus
import com.bhushan.turtlemintassignment.utils.EventInterface
import com.bhushan.turtlemintassignment.utils.Events
import com.bhushan.turtlemintassignment.utils.NetworkManager
import org.koin.android.ext.android.inject

abstract class AppFragment : Fragment(){
    private val TAG = "AppFragment"
    private var networkAvailabilityListener: EventInterface? = null
    private val networkManager : NetworkManager by inject()

    open fun onNetworkChanged(isConnected: Boolean) {
        Log.d(TAG, "onNetworkChanged")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkAvailabilityListener = object : EventInterface {
            override fun publishEvent(eventId: Int, data: Any?) {
                onNetworkChanged(data == true)
            }
        }
        if (!networkManager.isConnected) {
            // Updating offline network state to fragment if network not available at creation time
            onNetworkChanged(false)
        }
        EventBus.instance.addEvent(
                Events.EVENT_NETWORK_CONNECTIVITY_CHANGED,
                networkAvailabilityListener
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.instance.removeEvent(
                Events.EVENT_NETWORK_CONNECTIVITY_CHANGED,
                networkAvailabilityListener
        )
    }
}