package com.bhushan.turtlemintassignment.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.HashMap

class EventBus : EventBusInterface {
    private val eventMap: MutableMap<Int, MutableMap<Int, WeakReference<EventInterface>>> =
            HashMap()
    private val lock = Any()
    override fun addEvent(eventId: Int, callback: EventInterface?) {
        requireNotNull(callback)
        synchronized(lock) {
            var map = eventMap[eventId]
            if (map == null) {
                map = HashMap()
                eventMap[eventId] = map
            }
            map.put(System.identityHashCode(callback), WeakReference(callback))
        }
    }

    override fun addEvents(callback: EventInterface?, vararg eventIds: Int) {
        for (eventId in eventIds) {
            addEvent(eventId, callback)
        }
    }

    override fun removeEvent(eventId: Int, callback: EventInterface?) {
        requireNotNull(callback)
        synchronized(lock) {
            val map = eventMap[eventId]
            if (!map.isNullOrEmpty()) {
                map.remove(System.identityHashCode(callback))
                if (map.isEmpty()) {
                    eventMap.remove(eventId)
                }
            }
        }
    }

    override fun removeEvents(callback: EventInterface?, vararg eventIds: Int) {
        for (eventId in eventIds) {
            removeEvent(eventId, callback)
        }
    }

    override fun postEvent(eventId: Int, data: Any?) {
        synchronized(lock) {
            val map = eventMap[eventId]
            if (!map.isNullOrEmpty()) {
                val iterator = map.values.iterator()
                while (iterator.hasNext()) {
                    val event = iterator.next().get()
                    if (event == null) {
                        iterator.remove()
                    } else {
                        CoroutineScope(Dispatchers.Default).launch {
                            event.publishEvent(eventId, data)
                        }
                    }
                }
                if (map.isEmpty()) {
                    eventMap.remove(eventId)
                }
            }
        }
    }

    companion object {
        val instance  : EventBus by lazy { EventBus() }
    }
}