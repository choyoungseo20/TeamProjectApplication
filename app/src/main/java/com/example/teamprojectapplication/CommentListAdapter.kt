package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListCommentsBinding
import com.example.teamprojectapplication.Model.Post

class CommentListAdapter (private var comments: LiveData<MutableList<Post.Comment>>): RecyclerView.Adapter<CommentListAdapter.Holder>(){

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int, commentKey: String, userId: String)

    }
    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    inner class Holder(private val binding: ListCommentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Post.Comment?) {
            comment?.let {
                binding.listCommentsTextviewUserId.text = comment.userId
                binding.listCommentsTextviewComment.text = comment.content
                val commentKey = comment.commentKey

                binding.root.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION && itemClickListener != null) {
                        itemClickListener.onItemClick(itemView, pos, commentKey, comment.userId)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListCommentsBinding.inflate(LayoutInflater.from(parent.context))
        return  Holder(binding)
    }


    override fun getItemCount(): Int = comments.value?.size ?:0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(comments.value?.getOrNull(position))
    }


}