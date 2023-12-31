package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListDdaysBinding
import com.example.teamprojectapplication.Model.Post

class DdayListAdapter(val posts: LiveData<MutableList<Post>>) :RecyclerView.Adapter<DdayListAdapter.Holder>(){
    //firebase에서 가져오기 -> LiveData가 아님

    //클릭 리스너 역할을 하는 interface
    inner class Holder(private val binding: ListDdaysBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post?){
            post?.let {
                binding.imgShow.setImageResource( when( post.private ){
                    true -> R.drawable.hide
                    false -> R.drawable.show
                })
                binding.txtTitle.text = post.title
                binding.txtDate.text = post.date
                binding.txtDday.text = post.dday
                binding.ddayBackground.setBackgroundColor(post.color)
                binding.txtLike.text = post.likeCount.toString()
                binding.txtComment.text = post.commentCount.toString()
                val key = post.key

                binding.root.setOnClickListener {
                    val pos = adapterPosition
                    if(pos != RecyclerView.NO_POSITION && itemClickListener != null){
                        itemClickListener.onItemClick(itemView,pos, key )
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListDdaysBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(posts.value?.getOrNull(position))
    }
    override fun getItemCount(): Int = posts.value?.size ?:0



}