package com.bhushan.turtlemintassignment.utils

interface EventBusInterface {
    fun addEvent(eventId: Int, callback: EventInterface?)
    fun addEvents(callback: EventInterface?, vararg eventIds: Int)
    fun removeEvent(eventId: Int, callback: EventInterface?)
    fun removeEvents(callback: EventInterface?, vararg eventIds: Int)
    fun postEvent(eventId: Int, data: Any? = null)
}

interface EventInterface {
    fun publishEvent(eventId: Int, data: Any?)
}