package com.bhushan.turtlemintassignment.utils

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bhushan.turtlemintassignment.utils.networkUtility.Resource
import com.bhushan.turtlemintassignment.utils.networkUtility.Status
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * observeOnce extension helps to remove observer attached to LiveData for Status.SUCCESS|Status.ERROR,
 * to prevent duplicate onChanged call
 */
fun <T> LiveData<Resource<T>>.observeOnce(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<Resource<T>>
) {
    observe(lifecycleOwner, object : Observer<Resource<T>> {
        override fun onChanged(t: Resource<T>?) {
            observer.onChanged(t)
            if (t?.status == Status.SUCCESS || t?.status == Status.ERROR) {
                removeObserver(this)
            }
        }
    })
}

@SuppressLint("SimpleDateFormat")
fun getDate(dateString: String): String {
    val input = SimpleDateFormat(INPUT_DATE_FORMAT)
    val output = SimpleDateFormat(OUTPUT_DATE_FORMAT)
    var d: Date? = null
    try {
        d = input.parse(dateString)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return output.format(d)
}

fun getCommentIssueUrl(number: Int): String {
    return "$ISSUE_URL$number"
}

const val ISSUE_URL = "https://api.github.com/repos/square/okhttp/issues/"
const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"
const val OUTPUT_DATE_FORMAT = "MM-dd-yyyy"