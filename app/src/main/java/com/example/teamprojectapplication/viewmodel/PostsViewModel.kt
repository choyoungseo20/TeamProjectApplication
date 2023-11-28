package com.example.teamprojectapplication.viewmodel

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class   PostsViewModel : ViewModel() {
    private val _posts = MutableLiveData<MutableList<Post>>()
    val posts : LiveData<MutableList<Post>> =  _posts

    val nonPrivatePosts: LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filterNot { it.private } as MutableList<Post>
    }

    private val repository = PostsRepository()
    init {
        repository.observePost(_posts)
    }

    fun findKey(){
        repository.findKey()
    }

    fun setUser() {
        repository.setUser()
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
    fun setDday(newValue: String) {
        //date
        repository.postValue("date", newValue)
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

    fun setLike(newValue: Int) {
        repository.postValue("like", newValue.toString())
    }
    fun setComment(newValue: Int) {
        repository.postValue("comment", newValue.toString())
    }
    fun setPrivate(newValue: Boolean) {
        repository.private(newValue)
    }

    fun setColor(newValue: String) {
        repository.postValue("color", newValue.toString())
    }



}