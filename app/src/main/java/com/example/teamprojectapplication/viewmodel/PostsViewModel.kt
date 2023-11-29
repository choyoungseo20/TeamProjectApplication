package com.example.teamprojectapplication.viewmodel

import android.os.Build
import android.view.animation.Transformation
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class PostsViewModel : ViewModel() {
    private val _posts = MutableLiveData<MutableList<Post>>()
    val posts : LiveData<MutableList<Post>> get() = _posts

    val nonPrivatePosts: LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filterNot { it.private }.toMutableList().apply {
            reverse()
        }
    }


    private val repository = PostsRepository()
    init {
        repository.observePost(_posts)
    }

    /*
    private fun uploadPost(elem: String, newValue: String) {

        val currentPosts = _posts.value?: mutableListOf()
        val updatedPosts = currentPosts.map { post ->
            if ( elem == "title" ) {
                post.copy(title = newValue)
            }
            else if ( elem == "text" ) {
                post.copy(text = newValue)
            }
            else if ( elem == "date" ) {
                post.copy(date = newValue)
            }
            else if ( elem == "dday" ) {
                post.copy(dday = newValue)
            }
            else if ( elem == "like" ) {
                post.copy(like = newValue.toInt())
            }
            else if ( elem == "dday" ) {
                post.copy(comment = newValue.toInt())
            }
            else if ( elem == "private" ) {
                post.copy(private = newValue.toBoolean())
            }
            else if ( elem == "color" ) {
                post.copy(color = newValue)
            }
            else {
                post
            }
        }
        repository.exPost(updatedPosts)
    }

    fun findIndex() {
        repository.findIndex()
    }
    fun setUser() {
        repository.setUser()
    }
    fun setPost() {
        repository.setPost()
    }
    fun setTitle(newValue: String) {
        uploadPost("title", newValue)
    }
    fun setText(newValue: String) {
        uploadPost("text", newValue)
    }
    fun setDate(newValue: String) {
        uploadPost("date", newValue)
    }
    fun setDday(newValue: String) {
        uploadPost("dday", newValue)
    }
    fun setLike(newValue: String) {
        uploadPost("like", newValue)
    }
    fun setComment(newValue: String) {
        uploadPost("comment", newValue)
    }
    fun setPrivate(newValue: Boolean) {
        uploadPost("private", newValue.toString())
    }
    fun setColor(newValue: String) {
        uploadPost("color", newValue)
    }

     */


    //val key get() = _posts.value?



    fun findKey(postKey: String) {

    }
    fun setUser() {
        repository.setUser()
    }
    fun setPost() {
        repository.setPost()
    }
    fun setTitle(newValue: String) {
        repository.postValue("title", newValue)
    }
    fun setText(newValue: String) {
        repository.postValue("text", newValue)
    }
    fun setDate(newValue: String) {
        repository.postValue("date", newValue)
    }
    fun setDday(newValue: String) {
        //date
        setDate(newValue)
        //dday
        try {
            val selectedDate = LocalDate.parse(newValue, DateTimeFormatter.ISO_DATE)
            val difference = LocalDate.now().until(selectedDate, ChronoUnit.DAYS).toInt()
            val resOfDifference = when {
                difference > 0 -> "D-$difference"
                difference < 0 -> "D$difference"
                else -> "D-day"
            }
            repository.postValue("dday", resOfDifference)
        }catch (e:DateTimeParseException){
            repository.postValue("key","Invalid Date Format")
        }
    }
    val getDday get() = posts.value?.firstOrNull()?.dday

    fun setLikeCount(newValue: Int) {
        repository.postValue("likecount", newValue.toString())
    }
    fun setCommentCount(newValue: Int) {
        repository.postValue("commentCount", newValue.toString())
    }
    fun setPrivate(newValue: Boolean) {
        repository.private(newValue)
    }
    fun setColor(newValue: String) {
        repository.postValue("color", newValue)
    }
}