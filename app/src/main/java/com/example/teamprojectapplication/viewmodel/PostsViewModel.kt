package com.example.teamprojectapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey

class PostsViewModel : ViewModel() {
    private val _posts = MutableLiveData<MutableList<Post>>()
    val posts : LiveData<MutableList<Post>> = _posts

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




    fun findKey() {
        repository.findKey()
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
        repository.postValue("dday", newValue)
    }
    fun setLike(newValue: Int) {
        repository.postValue("like", newValue.toString())
    }
    fun setComment(newValue: Int) {
        repository.postValue("comment", newValue.toString())
    }
    fun setPrivate(newValue: Boolean) {
        repository.private(newValue)
    }
    fun setColor(newValue: Boolean) {
        repository.postValue("color", newValue.toString())
    }
}