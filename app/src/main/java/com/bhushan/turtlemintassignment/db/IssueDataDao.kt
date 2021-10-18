package com.bhushan.turtlemintassignment.db

import androidx.room.*
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.data.IssueData

@Dao
interface IssueDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssueData(issueData: List<IssueData>)

    @Query("SELECT * FROM IssueData")
    fun getIssueDataList(): List<IssueData>

    @Query("DELETE FROM IssueData")
    fun deleteAllIssueData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssueCommentData(issueData: List<IssueCommentData>)

    @Query("SELECT * FROM IssueCommentData where issue_url = :issueUrl")
    fun getIssueCommentDataList(issueUrl:String): List<IssueCommentData>

    @Query("DELETE FROM IssueCommentData")
    fun deleteAllIssueCommentData()
}