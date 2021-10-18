package com.bhushan.turtlemintassignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bhushan.turtlemintassignment.R
import com.bhushan.turtlemintassignment.data.IssueData
import com.bhushan.turtlemintassignment.utils.getDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class IssueDataAdapter(private val activity: FragmentActivity?) : RecyclerView.Adapter<IssueDataItemViewHolder>() {

    private val issueDataList: ArrayList<IssueData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueDataItemViewHolder {
        return IssueDataItemViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_issue_data_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IssueDataItemViewHolder, position: Int) {
        val issueData = issueDataList[position]
        holder.titleText.text = issueData.title.trim()
        holder.subtitleText.text = issueData.body.trim()
        holder.userNameText.text = issueData.user?.login
        Glide.with(holder.itemView)
            .load(issueData.user?.avatar_url?:"")
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .circleCrop()
            .placeholder(R.drawable.ic_user_profile)
            .error(R.drawable.ic_user_profile)
            .fallback(R.drawable.ic_user_profile)
            .into(holder.avatarImage)
        holder.updatedDateText.text = getDate(issueData.updated_at)
        activity?.let {
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(IssueCommentDataFragment.COMMENT_ISSUE_NUMBER, issueData.number)
                it.findNavController().navigate(R.id.issueCommentDataFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return issueDataList.size
    }

    fun submitList(items: List<IssueData>) {
        issueDataList.clear()
        issueDataList.addAll(items)
        notifyDataSetChanged()
    }
}

class IssueDataItemViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    val titleText: TextView = itemView.findViewById(R.id.title_text)
    val subtitleText: TextView = itemView.findViewById(R.id.subtitle_text)
    val avatarImage: ImageView = itemView.findViewById(R.id.avatar_image)
    val userNameText: TextView = itemView.findViewById(R.id.username_text)
    val updatedDateText: TextView = itemView.findViewById(R.id.updated_date_text)
}