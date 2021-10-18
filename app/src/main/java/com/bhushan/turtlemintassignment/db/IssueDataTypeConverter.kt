package com.bhushan.turtlemintassignment.db

import androidx.room.TypeConverter
import com.bhushan.turtlemintassignment.data.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IssueDataTypeConverter {
    @TypeConverter
    fun fromUserData(userData: UserData?): String? {
        val gson = Gson()
        return gson.toJson(userData)
    }

    @TypeConverter
    fun fromStringToUserData(value: String?): UserData? {
        val listType =
            object : TypeToken<UserData?>() {}.type
        return Gson().fromJson(value, listType)
    }
}