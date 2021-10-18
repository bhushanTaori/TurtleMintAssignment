package com.bhushan.turtlemintassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.data.IssueData

@Database(
    entities = [
        IssueData::class,
        IssueCommentData::class
    ],
    version = 1
)
@TypeConverters(
    IssueDataTypeConverter::class
)

abstract class IssueDataRoomDatabase : RoomDatabase() {
    abstract fun issueDataDao(): IssueDataDao
}