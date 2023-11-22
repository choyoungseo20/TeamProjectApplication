package com.example.teamprojectapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectapplication.repository.PostsRepository

data class Post(
    val id : String,
    val writeId : String,
    val title : String,
    val text : String,
    val createAt : String,
    val likeList : ArrayList<String>,
    val imgList : ArrayList<String>,
    val date : String,
    val dday : String,
    val like : Int,
    val comment : Int,
    val show : Boolean
)

class PostsViewModel : ViewModel() {
    private val repository = PostsRepository()

    private val _ddays = MutableLiveData<ArrayList<Post>>()
    val ddays : LiveData<ArrayList<Post>> = _ddays

    fun retrieveDdays() {

    }
}