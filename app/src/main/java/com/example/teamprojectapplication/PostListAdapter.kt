package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamprojectapplication.databinding.ListPostsBinding
import com.example.teamprojectapplication.Model.Post

class PostListAdapter(val posts: LiveData<MutableList<Post>>) :RecyclerView.Adapter<PostListAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding: ListPostsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post?){
            post?.let {
                Glide.with(itemView.context).load(it.imageUrl).into(binding.listsPostsImageviewContent)
                binding.listsPostsExplainTextview.text = it.text
                binding.listsPostsProfileTextview.text = it.email
                binding.listsPostsLikecounter.text = it.likeCount.toString()
                binding.listsPostsCommentscounter.text = it.commentCount.toString()
                val key = it.key

                binding.root.setOnClickListener {
                    val pos = adapterPosition
                    if( pos != RecyclerView.NO_POSITION && itemClickListener != null ) {
                        itemClickListener.onItemClick(itemView, pos, key)
                    }
                }
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int, key: String)
    }
    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts.value?.getOrNull(position))
    }
    override fun getItemCount(): Int = posts.value?.size ?:0
}