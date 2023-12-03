package com.example.teamprojectapplication.viewmodel

import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository
import java.time.format.DateTimeParseException

class PostsViewModel : ViewModel() {
    private val repository = PostsRepository()
    private val _posts = MutableLiveData<MutableList<Post>>()
    private val _isLiked = MutableLiveData<Boolean>()
    private val _comments = MutableLiveData<MutableList<Post.Comment>>()
    val isLiked: LiveData<Boolean> get() = _isLiked
    private val _likeStatusMap = mutableMapOf<String, MutableLiveData<Boolean>>()


    val posts : LiveData<MutableList<Post>> get() = _posts

    val nonPrivatePosts: LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filterNot { it.private }.toMutableList().apply {
            reverse()
        }
    }

    //현재 접속자의 id와 일치하는 포스트만 필터링하여 뜨우는 함수
    val myPosts:LiveData<MutableList<Post>> = _posts.map{ postList ->
        postList.filter{it.email == repository.getCurrUserEmail()}.toMutableList().apply{
            reverse()
        }
    }

    val comments : LiveData<MutableList<Post.Comment>> get() = _comments


    init {
        repository.observePost(_posts)
    }

    private val _post = MutableLiveData(Post())
    val post: LiveData<Post>
        get() = _post

    var key: String? = null

    fun bringKey(postKey: String) {
        Log.d("func","bringkey called")
        key = postKey
    }

    fun retriveKey(): String? {
        return key
    }

    fun setUser() {
        repository.setUser()
    }

    fun setTitle(title: String) {
        _post.value = _post.value?.copy(
            title = title
        )
    }
    fun setText(text: String) {
        _post.value = _post.value?.copy(
            text = text
        )
    }
    fun setDate(date: String) {
        _post.value = _post.value?.copy(
            date = date
        )
    }
    fun calDiffernce(date: String) : String {
        try{
            val selectedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
            val difference = LocalDate.now().until(selectedDate, ChronoUnit.DAYS).toInt()
            val resOfDifference = when {
                difference > 0 -> "D-$difference"
                difference < 0 -> "D$difference"
                else -> "D-day"
            }
            return resOfDifference
        }catch (e: DateTimeParseException){
            return "Error"
        }

    }
    fun setDday(dday: String) {
        val ddayData = calDiffernce(dday)
        _post.value = _post.value?.copy(
            dday = ddayData
        )
    }
    fun setPrivate(private: Boolean) {
        _post.value = _post.value?.copy(
            private = private
        )
    }
    private fun rgbTohex(color: Int): String{
        val hexRed = color.red.toString(16).padStart(2, '0')
        val hexGreen = color.green.toString(16).padStart(2, '0')
        val hexBlue = color.blue.toString(16).padStart(2, '0')

        return "#$hexRed$hexGreen$hexBlue"
    }

    fun setColor(color: Int){
        val colorData = rgbTohex(color)
        _post.value = _post.value?.copy(
            color = colorData
        )
    }
    fun setPost() {
        repository.setPost(post.value)
    }

    private val _comment = MutableLiveData(Post.Comment())
    val comment: LiveData<Post.Comment>
        get() = _comment

    fun addComment(postKey: String, comment: Post.Comment) {
        repository.addComment(postKey, comment)
        //댓글 추가될 때마다 댓글 개수 업데이트
        val currentCommentCount = _comments.value?.size ?: 0
        val newCommentCount = currentCommentCount + 1
        repository.updateCommentCount(postKey, newCommentCount)
    }

    fun observeComments(postKey: String) {
        repository.observeComments(postKey, _comments)
    }
    /*
    fun observeCommentCount(postKey: String): LiveData<Int> {
        val commentCountLiveData = MutableLiveData<Int>()

        repository.observeComments(postKey, _comments)
        _comments.observeForever{
            commentCountLiveData.value = it.size
        }
        return commentCountLiveData
    }*/

    fun bringEmail(key: String, callback: (String?)-> Unit) {
        repository.bringContent(key, "email", callback)
    }

    fun bringText(key: String, callback: (String?)-> Unit)  {
        repository.bringContent(key, "text", callback)
    }

    // 게시물의 좋아요 상태를 얻거나 없으면 생성하는 함수
    private fun getLikeStatusLiveData(postKey: String): MutableLiveData<Boolean> {
        return _likeStatusMap.getOrPut(postKey) { MutableLiveData() }
    }

    // 게시물의 좋아요 상태를 관찰하는 함수
    fun observeLikeStatus(postKey: String): LiveData<Boolean> {
        return getLikeStatusLiveData(postKey)
    }

    fun likePost(postKey: String) {
        val userId = repository.fbAuth?.currentUser?.uid

        if (userId != null) {
            repository.likePost(postKey, userId)
            val likeStatusLiveData = getLikeStatusLiveData(postKey)
            likeStatusLiveData.value = if (likeStatusLiveData.value == null) {
                true
            } else {
                !(likeStatusLiveData.value!!)
            }
        }
    }

}