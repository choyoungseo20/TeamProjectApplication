package com.example.teamprojectapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectapplication.repository.PostsRepository

data class Post(
    val email: String = "",
    val writeId: String = "",
    val title: String = "",
    val text: String = "",
    val createAt: String = "",
    val likeList: ArrayList<String> = ArrayList(),
    val imgList: ArrayList<String> = ArrayList(),
    val date: String = "",
    val dday: String = "",
    val like: Int = 0,
    val comment: Int = 0,
    val private: Boolean = false
)

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

    fun setPost() {
        repository.setPost()
    }

    fun exPost(post: Post){
        repository.exPost(post)

    }
    fun setEmail(newValue: String) {
        repository.postValue("email", newValue)
    }
    fun setWriteId(newValue: String) {
        repository.postValue("writeId", newValue)
    }
    fun setTitle(newValue: String) {
        repository.postValue("title", newValue)
    }
    fun setText(newValue: String) {
        repository.postValue("text", newValue)
    }
    fun setCreateAt(newValue: String) {
        repository.postValue("createAt", newValue)
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

}