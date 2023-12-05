package com.example.teamprojectapplication.viewmodel

import android.net.Uri
import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.teamprojectapplication.Post
import com.example.teamprojectapplication.repository.PostsRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.values
import java.time.format.DateTimeParseException
import kotlin.math.abs

class PostsViewModel : ViewModel() {
    private val repository = PostsRepository()
    private val _posts = MutableLiveData<MutableList<Post>>()
    val posts : LiveData<MutableList<Post>> get() = _posts
    private val _post = MutableLiveData(Post())
    val post: LiveData<Post> get() = _post
    private val _isLiked = MutableLiveData<Boolean>()
    val isLiked: LiveData<Boolean> get() = _isLiked
    private val _comments = MutableLiveData<MutableList<Post.Comment>>()
    val comments : LiveData<MutableList<Post.Comment>> get() = _comments
    private val _comment = MutableLiveData(Post.Comment())
    val comment: LiveData<Post.Comment> get() = _comment
    private val _likeStatusMap = mutableMapOf<String, MutableLiveData<Boolean>>()
    lateinit var key: String

    init {
        repository.observePost(_posts)
    }

    val nonPrivatePosts: LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filterNot { it.private }.toMutableList().apply {
            reverse()
        }
    }

    //현재 접속자의 id와 일치하는 포스트만 필터링하여 뜨우는 함수
    val myPosts:LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filter { it.email == FirebaseAuth.getInstance().currentUser?.email }
            .toMutableList().apply {
            reverse()
            for (post in this) {
                // 예시: 현재 dday 값을 10으로 변경
                post.dday = calDiffernce(post.date)
                // 또는 원하는 로직에 따라 dday 값을 변경
            }
        }
    }


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
            val today = LocalDate.now()
            val difference = today.until(selectedDate, ChronoUnit.DAYS).toInt()
            val resOfDifference = when {
                selectedDate.isBefore(today) -> "D+${abs(difference)}"
                selectedDate.isAfter(today) -> "D-$difference"
                else -> "D-day"
            }
            return resOfDifference
        }catch (e: DateTimeParseException){
            return "Error"
        }

    }
    /*fun setDday(dday: String) {
        val ddayData = calDiffernce(dday)
        _post.value = _post.value?.copy(
            dday = ddayData
        )
    }

     */
    fun setImageUrl(url: String) {
        _post.value = _post.value?.copy(
            imageUrl = url
        )
    }
    fun setPrivate(private: Boolean) {
        _post.value = _post.value?.copy(
            private = private
        )
    }
    /*
    private fun rgbTohex(color: Int): String{
        val hexRed = color.red.toString(16).padStart(2, '0')
        val hexGreen = color.green.toString(16).padStart(2, '0')
        val hexBlue = color.blue.toString(16).padStart(2, '0')

        return "#$hexRed$hexGreen$hexBlue"
    }

     */
    fun setColor(color: Int){
        //val colorData = rgbTohex(color)
        _post.value = _post.value?.copy(
            color = color
        )
    }
    fun setPost() = repository.setPost(post.value)

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

    fun bringEmail(key: String, callback: (String?)-> Unit) {
        repository.bringContent(key, "email", callback)
    }

    fun bringText(key: String, callback: (String?)-> Unit)  {
        repository.bringContent(key, "text", callback)
    }

    fun searchWord(word : String) {
        repository.searchWord(word)
    }

    fun getSearchWord(): LiveData<String?> {
        val userId = repository.fbAuth?.currentUser?.uid
            ?: return MutableLiveData<String?>().apply { value = null }
        return repository.getWord()
    }

    val searchPosts: LiveData<MutableList<Post>> = _posts.map { postList ->
        val word = getSearchWord().value // LiveData의 값을 가져옴
        postList.filter { !it.private && (it.email == word) }.toMutableList().apply {
            reverse()
        }
    }

    fun likePost(postKey: String) {
        val userId = repository.fbAuth?.currentUser?.uid
        repository.likePost(postKey, userId!!)
    }
    fun observeLikeStatus(postKey: String, userId: String): LiveData<Boolean> {
        return repository.observeLikeStatus(postKey, userId)
    }

    fun imageUpload(uri: Uri) {
        repository.imageUpload(uri)
    }

}