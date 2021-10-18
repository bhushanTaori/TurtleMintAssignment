package com.bhushan.turtlemintassignment.viewModel

import androidx.lifecycle.ViewModel
import com.bhushan.turtlemintassignment.network.IssueDataRepository

class IssueDataViewModel(private val issueDataRepository: IssueDataRepository) : ViewModel() {
    fun getIssueData() = issueDataRepository.getIssueData()

    fun getIssueCommentData(number:Int) = issueDataRepository.getIssueCommentData(number)
}