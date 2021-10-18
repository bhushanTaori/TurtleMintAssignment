package com.bhushan.turtlemintassignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhushan.turtlemintassignment.R
import com.bhushan.turtlemintassignment.databinding.FragmentIssueCommentDataBinding
import com.bhushan.turtlemintassignment.utils.dbUtility.DatabaseHelper
import com.bhushan.turtlemintassignment.utils.getCommentIssueUrl
import com.bhushan.turtlemintassignment.utils.networkUtility.Status
import com.bhushan.turtlemintassignment.utils.observeOnce
import com.bhushan.turtlemintassignment.viewModel.IssueDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class IssueCommentDataFragment : AppFragment() {

    private val issueDataViewModel by viewModel<IssueDataViewModel>()
    private lateinit var issueCommentDataAdapter: IssueCommentDataAdapter
    private lateinit var fragmentIssueCommentDataBinding: FragmentIssueCommentDataBinding

    companion object {
        const val COMMENT_ISSUE_NUMBER = "commentIssueNumber"
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        super.onNetworkChanged(isConnected)
        CoroutineScope(Dispatchers.Main).launch {
            if (isConnected) {
                showSnackBar(getString(R.string.online))
                loadData(arguments?.getInt(COMMENT_ISSUE_NUMBER) ?: 0)
            } else {
                showSnackBar(getString(R.string.offline))
            }
        }
    }

    private fun showSnackBar(text: String) {
        if (view != null) {
            val snack = Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG)
            snack.show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentIssueCommentDataBinding = FragmentIssueCommentDataBinding.inflate(inflater, container, false)
        return fragmentIssueCommentDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindTitle()
        issueCommentDataAdapter = IssueCommentDataAdapter()
        bindRecyclerView()
    }

    private fun bindTitle() {
        activity?.title = getString(R.string.comments)
    }

    override fun onStart() {
        super.onStart()
        loadData(arguments?.getInt(COMMENT_ISSUE_NUMBER) ?: 0)
    }

    private fun bindRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentIssueCommentDataBinding.issueDataSectionRV.isNestedScrollingEnabled = false
        fragmentIssueCommentDataBinding.issueDataSectionRV.layoutManager = linearLayoutManager
        fragmentIssueCommentDataBinding.issueDataSectionRV.adapter = issueCommentDataAdapter
    }

    /**
     * Below operations are taken care,
     * 1. Hit https://api.github.com/repos/square/okhttp/issues/{number}/comments api & store IssueCommentData inside Room db
     *
     * 2. Retrieve listOf Issue comment Data from room db & populate on UI.
     *
     * 3. Error occurred then show failure toast & displays old room db list on UI.
     */
    private fun loadData(issueNumber: Int) {
        fragmentIssueCommentDataBinding.loadingBar.visibility = View.VISIBLE
        issueDataViewModel.getIssueCommentData(issueNumber)
                .observeOnce(this, {
                    if (it.status == Status.SUCCESS && !it.data.isNullOrEmpty()) {
                        fragmentIssueCommentDataBinding.loadingBar.visibility = View.GONE
                        issueCommentDataAdapter.submitList(it.data)
                        showNoDataTextIfApplicable(it.data.isEmpty())
                    } else if (it.status == Status.SUCCESS || it.status == Status.ERROR) {
                        issueCommentDataAdapter.submitList(
                                DatabaseHelper.getIssueCommentDataList(getCommentIssueUrl(issueNumber))
                                        ?: Collections.emptyList())
                        showNoDataTextIfApplicable(it.data?.isEmpty() == true)
                        Toast.makeText(context, resources.getString(R.string.failed_data_load, it.message), Toast.LENGTH_LONG)
                                .show()
                        fragmentIssueCommentDataBinding.loadingBar.visibility = View.GONE
                    }
                })
    }

    private fun showNoDataTextIfApplicable(isListEmpty: Boolean) {
        if (isListEmpty) {
            fragmentIssueCommentDataBinding.noDataText.visibility = View.VISIBLE
        } else {
            fragmentIssueCommentDataBinding.noDataText.visibility = View.GONE
        }
    }
}