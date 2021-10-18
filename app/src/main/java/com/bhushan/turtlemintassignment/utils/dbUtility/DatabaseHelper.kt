package com.bhushan.turtlemintassignment.utils.dbUtility

import android.content.Context
import androidx.room.Room
import androidx.room.Transaction
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.data.IssueData
import com.bhushan.turtlemintassignment.db.IssueDataRoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * It helps to do below operations,
 * ------------------------------------------------
 *  init | insertIssueData | getIssueDataList | insertIssueCommentData | getIssueCommentDataList
 * ------------------------------------------------
 */

object DatabaseHelper {
    private const val DB_NAME = "IssueDataRoomDatabase"
    lateinit var roomDb: IssueDataRoomDatabase

    fun init(context: Context) {
        roomDb = Room.databaseBuilder(
            context,
            IssueDataRoomDatabase::class.java, DB_NAME
        ).build()
    }

    private fun getDBInstance(): IssueDataRoomDatabase? {
        if (DatabaseHelper::roomDb.isInitialized) {
            return roomDb
        }

        throw Exception("Room Database not initialized")
    }

    @Transaction
    fun insertIssueData(issueData: List<IssueData>) {
        runBlocking {
            val job = GlobalScope.launch {
                getDBInstance()!!.issueDataDao()
                    .insertIssueData(issueData)
            }
            job.join()
        }
    }

    @Transaction
    fun getIssueDataList(): List<IssueData>? {
        var list: List<IssueData>? = null
        runBlocking {
            val job = GlobalScope.launch {
                list = getDBInstance()!!.issueDataDao().getIssueDataList()
            }
            job.join()
        }
        return list
    }

    @Transaction
    fun insertIssueCommentData(issueCommentData: List<IssueCommentData>) {
        runBlocking {
            val job = GlobalScope.launch {
                getDBInstance()!!.issueDataDao()
                        .insertIssueCommentData(issueCommentData)
            }
            job.join()
        }
    }

    @Transaction
    fun getIssueCommentDataList(issueUrl:String): List<IssueCommentData>? {
        var list: List<IssueCommentData>? = null
        runBlocking {
            val job = GlobalScope.launch {
                list = getDBInstance()!!.issueDataDao().getIssueCommentDataList(issueUrl)
            }
            job.join()
        }
        return list
    }
}