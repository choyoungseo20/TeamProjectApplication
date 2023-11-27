package com.example.teamprojectapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey

class PostsViewModel : ViewModel() {
    private val repository = PostsRepository()

    private val _posts = MutableLiveData<MutableList<Post>>()
    //val posts : LiveData<MutableList<Post>> = _posts

    fun fetchData(): LiveData<MutableList<Post>> {
        repository.observePost().observeForever{
            _posts.value = it
        }
        return _posts

    }


    fun findIndex() {
        repository.findIndex()
    }
    fun setPost() {
        repository.setPost()
    }
    fun setEmail(newValue: String) {
        repository.postValue("email", newValue)
    }
    fun setTitle(newValue: String) {
        repository.postValue("title", newValue)
    }
    fun setText(newValue: String) {
        repository.postValue("text", newValue)
    }
    fun setLikeList(newValue: ArrayList<String>) {
        repository.postValue("likeList", newValue.toString())
    }
    fun setImgList(newValue: ArrayList<String>) {
        repository.postValue("imgList", newValue.toString())
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