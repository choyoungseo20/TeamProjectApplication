package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListPostsBinding
import com.example.teamprojectapplication.databinding.ListSearchBinding

class SearchPostAdapter(val posts: LiveData<MutableList<Post>>):
    RecyclerView.Adapter<SearchPostAdapter.ViewHolder>() {

    class ViewHolder(binding: ListSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post?){
            post?.let {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts.value?.getOrNull(position))


    }

    override fun getItemCount(): Int = posts.value?.size ?: 0

}