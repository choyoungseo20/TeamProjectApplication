package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListDdaysBinding
import com.example.teamprojectapplication.viewmodel.Post

class DdayListAdapter(val posts: LiveData<ArrayList<Post>>) :RecyclerView.Adapter<DdayListAdapter.Holder>(){
    //firebase에서 가져오기 -> LiveData가 아님

    //클릭 리스너 역할을 하는 interface
    inner class Holder(private val binding: ListDdaysBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post?){
            post?.let {
                binding.imgShow.setImageResource( when( post.show ){
                    true -> R.drawable.show
                    false -> R.drawable.hide
                })
                binding.txtTitle.text = post.title
                binding.txtDate.text = post.date
                binding.txtDday.text = post.dday
                binding.txtLike.text = post.like.toString()
                binding.txtComment.text = post.comment.toString()

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
        val binding = ListDdaysBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(posts.value?.getOrNull(position))
    }
    override fun getItemCount(): Int = posts.value?.size ?:0



}