package com.bhushan.turtlemintassignment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IssueCommentData(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "body")
        var body: String = "",
        @ColumnInfo(name = "user")
        var user: UserData? = null,
        @ColumnInfo(name = "issue_url")
        var issue_url: String? = null,
        @ColumnInfo(name = "updated_at")
        var updated_at: String = ""
)