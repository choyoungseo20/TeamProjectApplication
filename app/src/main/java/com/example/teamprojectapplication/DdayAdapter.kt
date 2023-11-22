package com.example.teamprojectapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.ListDdaysBinding

class DdayAdapter(val ddays: LiveData<ArrayList<Dday>>) :RecyclerView.Adapter<DdayAdapter.Holder>(){
    //클릭 리스너 역할을 하는 interface
    inner class Holder(private val binding: ListDdaysBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(dday: Dday?){
            dday?.let {
                binding.imgShow.setImageResource( when( dday.show ){
                    true -> R.drawable.show
                    false -> R.drawable.hide
                })
                binding.txtTitle.text = dday.title
                binding.txtDate.text = dday.date
                binding.txtDday.text = dday.dday
                binding.txtLike.text = dday.like.toString()
                binding.txtComment.text = dday.comment.toString()

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
        holder.bind(ddays.value?.getOrNull(position))
    }
    override fun getItemCount(): Int = ddays.value?.size ?:0



}