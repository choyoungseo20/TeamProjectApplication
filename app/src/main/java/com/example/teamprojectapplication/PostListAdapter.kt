package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListPostsBinding

class PostListAdapter(val posts: LiveData<MutableList<Post>>) :RecyclerView.Adapter<PostListAdapter.Holder>(){
    //firebase에서 가져오기 -> LiveData가 아님 observe로

    //클릭 리스너 역할을 하는 interface
    inner class Holder(private val binding: ListPostsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post?){
            post?.let {
                binding.listsPostsExplainTextview.text = post.text
                //binding.listsPostsCommentImageview = post.imgList
                binding.listsPostsProfileTextview.text = post.email
                binding.listsPostsLikecounter.text = post.like.toString()
                binding.listsPostsCommentscounter.text = post.comment.toString()

                binding.root.setOnClickListener {
                    val pos = adapterPosition
                    if(pos != RecyclerView.NO_POSITION && itemClickListener != null){
                        itemClickListener.onItemClick(itemView,pos)
                    }
                }
            }

        }

    }
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)

    }
    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListPostsBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(posts.value?.getOrNull(position))
    }
    override fun getItemCount(): Int = posts.value?.size ?:0


}