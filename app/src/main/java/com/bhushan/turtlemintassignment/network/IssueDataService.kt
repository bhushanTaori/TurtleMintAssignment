package com.bhushan.turtlemintassignment.network

import androidx.lifecycle.LiveData
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.data.IssueData
import com.bhushan.turtlemintassignment.utils.networkUtility.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IssueDataService  {
    @GET("issues")
    fun getIssueData(): LiveData<ApiResponse<List<IssueData>>>

    @GET("issues/{number}/comments")
    fun getIssueCommentData(@Path("number") number: Int): LiveData<ApiResponse<List<IssueCommentData>>>
}