package com.bhushan.turtlemintassignment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IssueData(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "body")
        var body: String = "",
        @ColumnInfo(name = "user")
        var user: UserData? = null,
        @ColumnInfo(name = "number")
        var number: Int = 0,
        @ColumnInfo(name = "updated_at")
        var updated_at: String = ""
)