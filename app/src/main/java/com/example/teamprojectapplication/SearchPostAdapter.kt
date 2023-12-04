package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListSearchBinding

class SearchPostAdapter(private val posts: LiveData<MutableList<Post>>) :
    RecyclerView.Adapter<SearchPostAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post?) {
            post?.let {
                binding.txtSearchCommentcount.text = it.commentCount.toString()
                binding.txtSearchContent.text = it.text
                binding.txtSearchUsername.text = it.email
                binding.txtSearchTitle.text = it.title
                binding.txtSearchLikecount.text = it.likeCount.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts.value?.getOrNull(position))
    }

    override fun getItemCount(): Int = posts.value?.size ?: 0
}
