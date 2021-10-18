package com.bhushan.turtlemintassignment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhushan.turtlemintassignment.R
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.utils.getDate

class IssueCommentDataAdapter :
    RecyclerView.Adapter<IssueCommentDataItemViewHolder>() {

    private val issueCommentDataList: ArrayList<IssueCommentData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueCommentDataItemViewHolder {
        return IssueCommentDataItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment_data_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IssueCommentDataItemViewHolder, position: Int) {
        val issueData = issueCommentDataList[position]
        holder.titleText.text = issueData.body.trim()
        holder.userNameText.text = issueData.user?.login
        holder.updatedDateText.text = getDate(issueData.updated_at)
    }

    override fun getItemCount(): Int {
        return issueCommentDataList.size
    }

    fun submitList(items: List<IssueCommentData>) {
        issueCommentDataList.clear()
        issueCommentDataList.addAll(items)
        notifyDataSetChanged()
    }
}

class IssueCommentDataItemViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    val titleText: TextView = itemView.findViewById(R.id.title_text)
    val userNameText: TextView = itemView.findViewById(R.id.username_text)
    val updatedDateText: TextView = itemView.findViewById(R.id.updated_date_text)
}