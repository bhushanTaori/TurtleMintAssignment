package com.bhushan.turtlemintassignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhushan.turtlemintassignment.R
import com.bhushan.turtlemintassignment.databinding.FragmentIssueDataBinding
import com.bhushan.turtlemintassignment.utils.dbUtility.DatabaseHelper
import com.bhushan.turtlemintassignment.utils.networkUtility.Status
import com.bhushan.turtlemintassignment.utils.observeOnce
import com.bhushan.turtlemintassignment.viewModel.IssueDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class IssueDataFragment : AppFragment() {
    private val issueDataViewModel by viewModel<IssueDataViewModel>()
    private lateinit var issueDataAdapter: IssueDataAdapter
    private lateinit var fragmentIssueDataBinding: FragmentIssueDataBinding

    override fun onNetworkChanged(isConnected: Boolean) {
        super.onNetworkChanged(isConnected)
        CoroutineScope(Dispatchers.Main).launch {
            if (isConnected) {
                showSnackBar(getString(R.string.online))
                loadData()
            } else {
                showSnackBar(getString(R.string.offline))
            }
        }
    }

    private fun bindTitle() {
        activity?.title = getString(R.string.app_name)
    }

    private fun showSnackBar(text: String) {
        if (view != null) {
            val snack = Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG)
            snack.show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { fragmentIssueDataBinding = FragmentIssueDataBinding.inflate(inflater, container, false)
        return fragmentIssueDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindTitle()
        issueDataAdapter = IssueDataAdapter(activity)
        bindRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun bindRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentIssueDataBinding.issueDataSectionRV.isNestedScrollingEnabled = false
        fragmentIssueDataBinding.issueDataSectionRV.layoutManager = linearLayoutManager
        fragmentIssueDataBinding.issueDataSectionRV.adapter = issueDataAdapter
    }

    /**
     * Below operations are taken care,
     * 1. Hit https://api.github.com/repos/square/okhttp/issues api & store IssueData inside Room db
     *
     * 2. Retrieve listOf IssueData from room db & populate on UI.
     *
     * 3. Error occurred then show failure toast & displays old room db list on UI.
     */
    private fun loadData() {
        fragmentIssueDataBinding.loadingBar.visibility = View.VISIBLE
        issueDataViewModel.getIssueData()
                .observeOnce(this, {
                    if (it.status == Status.SUCCESS && !it.data.isNullOrEmpty()) {
                        fragmentIssueDataBinding.loadingBar.visibility = View.GONE
                        issueDataAdapter.submitList(it.data)
                        showNoDataTextIfApplicable(it.data.isEmpty())
                    } else if (it.status == Status.SUCCESS || it.status == Status.ERROR) {
                        issueDataAdapter.submitList(DatabaseHelper.getIssueDataList()
                                ?: Collections.emptyList())
                        showNoDataTextIfApplicable(it.data?.isEmpty() == true)
                        Toast.makeText(context, resources.getString(R.string.failed_data_load, it.message), Toast.LENGTH_LONG)
                                .show()
                        fragmentIssueDataBinding.loadingBar.visibility = View.GONE
                    }
                })
    }

    private fun showNoDataTextIfApplicable(isListEmpty: Boolean) {
        if (isListEmpty) {
            fragmentIssueDataBinding.noDataText.visibility = View.VISIBLE
        } else {
            fragmentIssueDataBinding.noDataText.visibility = View.GONE
        }
    }
}