package com.bhushan.turtlemintassignment.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.data.IssueData
import com.bhushan.turtlemintassignment.utils.dbUtility.DatabaseHelper
import com.bhushan.turtlemintassignment.utils.getCommentIssueUrl
import com.bhushan.turtlemintassignment.utils.networkUtility.ApiResponse
import com.bhushan.turtlemintassignment.utils.networkUtility.AppExecutors
import com.bhushan.turtlemintassignment.utils.networkUtility.NetworkBoundResource
import com.bhushan.turtlemintassignment.utils.networkUtility.Resource

/**
 * Repository responsible to do n/w & room db operations
 * then return data to caller
 */

class IssueDataRepository constructor(
    private val appExecutors: AppExecutors,
    private val issueDataService: IssueDataService
) {

    fun getIssueData(): LiveData<Resource<List<IssueData>>> {
        return object : NetworkBoundResource<List<IssueData>, List<IssueData>>(appExecutors) {
            override fun saveCallResult(item: List<IssueData>) {
                DatabaseHelper.insertIssueData(item)
            }

            override fun shouldFetch(data: List<IssueData>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<IssueData>> {
                return MutableLiveData(DatabaseHelper.getIssueDataList())
            }

            override fun createCall(): LiveData<ApiResponse<List<IssueData>>> {
                return issueDataService.getIssueData()
            }

        }.asLiveData()
    }


    fun getIssueCommentData(number: Int): LiveData<Resource<List<IssueCommentData>>> {
        return object : NetworkBoundResource<List<IssueCommentData>, List<IssueCommentData>>(appExecutors) {
            override fun saveCallResult(item: List<IssueCommentData>) {
                DatabaseHelper.insertIssueCommentData(item)
            }

            override fun shouldFetch(data: List<IssueCommentData>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<IssueCommentData>> {
                val issueUrl = getCommentIssueUrl(number)
                return MutableLiveData(DatabaseHelper.getIssueCommentDataList(issueUrl))
            }

            override fun createCall(): LiveData<ApiResponse<List<IssueCommentData>>> {
                return issueDataService.getIssueCommentData(number)
            }

        }.asLiveData()
    }
}