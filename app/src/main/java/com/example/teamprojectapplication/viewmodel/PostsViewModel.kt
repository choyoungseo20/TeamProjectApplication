package com.example.teamprojectapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository

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

    private val _post = MutableLiveData(Post())
    val post: LiveData<Post>
        get() = _post


    fun setUser() {
        repository.setUser()
    }

    fun setTitle(title: String){
        _post.value = _post.value?.copy(
            title = title
        )
    }
    fun setText(text: String){
        _post.value = _post.value?.copy(
            text = text
        )
    }
    fun setDate(date: String){
        _post.value = _post.value?.copy(
            date = date
        )
    }
    fun setDday(dday: String){
        _post.value = _post.value?.copy(
            dday = dday
        )
    }
    fun setPrivate(private: Boolean){
        _post.value = _post.value?.copy(
            private = private
        )
    }
    fun setColor(color: String){
        _post.value = _post.value?.copy(
            color = color
        )
    }
    fun setPost(){
        repository.setPost(post.value)
    }

    /*fun findKey(postKey: String) {

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

     */
}