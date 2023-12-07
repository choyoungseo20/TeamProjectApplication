package com.example.teamprojectapplication.viewmodel

import android.net.Uri
import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.teamprojectapplication.Model.Post
import com.example.teamprojectapplication.Model.PostsRepository
import java.text.SimpleDateFormat
import java.time.format.DateTimeParseException
import java.util.Date
import kotlin.math.abs

class postViewModel : ViewModel() {
    private val repository = PostsRepository()
    private val _posts = MutableLiveData<MutableList<Post>>()
    val posts : LiveData<MutableList<Post>> get() = _posts

    private val _post = MutableLiveData(Post())
    val post: LiveData<Post> get() = _post


    private val _comments = MutableLiveData<MutableList<Post.Comment>>()
    val comments : LiveData<MutableList<Post.Comment>> get() = _comments


    private val _searchWord = MutableLiveData<String>()
    val searchWord : LiveData<String> get() = _searchWord

    private lateinit var key: String
    private lateinit var cKey: String

    init {
        repository.observePost(_posts)
    }

    val nonPrivatePosts: LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filterNot { it.private }.toMutableList().apply {
            reverse()
        }
    }

    //현재 접속자의 id와 일치하는 포스트만 필터링하여 뜨우는 함수 (ksh)
    val myPosts:LiveData<MutableList<Post>> = _posts.map { postList ->
        postList.filter { it.email == repository.fbAuth.currentUser?.email }
            .toMutableList().apply {
            reverse()
            for (post in this) {
                // 예시: 현재 dday 값을 10으로 변경
                post.dday = calDiffernce(post.date)
                // 또는 원하는 로직에 따라 dday 값을 변경
            }
        }
    }

    //공개 포스트 중 작성자를 기준으로 게시글 검색 (박승진)
    val searchPosts: LiveData<MutableList<Post>> = MediatorLiveData<MutableList<Post>>().apply {
        // 관찰 하려는 LiveData (nonPrivatePosts , searchWord)
        addSource(nonPrivatePosts) { nonPrivatePosts ->
            _searchWord.value?.let {
                value = nonPrivatePosts.filter { it.email == _searchWord.value }.toMutableList().apply {
                    reverse()
                }
            }
        }

        addSource(searchWord) { searchTerm ->
            val nonPrivatePostsValue = nonPrivatePosts.value
            nonPrivatePostsValue?.let {
                value = nonPrivatePostsValue.filter { it.email == searchTerm }.toMutableList().apply {
                    reverse()
                }
            }
        }
        // 두 LiveData의 변경을 observe하고, filter를 수행하여 searchPosts를 업데이트 함.
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

    fun setPost() = repository.setPost(post.value)

    // ksh
    fun addComment(postKey: String, comment: Post.Comment) {
        repository.addComment(postKey, comment)
        //댓글 추가될 때마다 댓글 개수 업데이트
        val currentCommentCount = _comments.value?.size ?: 0
        val newCommentCount = currentCommentCount + 1
        repository.updateCommentCount(postKey, newCommentCount)
    }

    fun deleteComment(postKey: String, comment: Post.Comment) {
        repository.deleteComment(postKey, comment)
        val currentCommentCount = _comments.value?.size ?: 0
        val newCommentCount = currentCommentCount - 1
        repository.updateCommentCount(postKey, newCommentCount)
    }

    fun observeComments(postKey: String) {
        repository.observeComments(postKey, _comments)
    }

    fun likePost(postKey: String) {
        val userId = repository.fbAuth?.currentUser?.uid
        repository.likePost(postKey, userId!!)
    }
    fun observeLikeStatus(postKey: String, userId: String): LiveData<Boolean> {
        return repository.observeLikeStatus(postKey, userId)
    }

    fun deletePost(postKey: String) {
        repository.deletePost(postKey)
    }


    fun bringEmail(key: String, callback: (String?)-> Unit) {
        repository.bringContent(key, "email", callback)
    }

    fun bringText(key: String, callback: (String?)-> Unit)  {
        repository.bringContent(key, "text", callback)
    }

    fun bringImage(key: String, callback: (String?)-> Unit) {
        repository.bringContent(key, "imageUrl", callback)
    }

    fun getCurrUserEmail(): String? {
        return repository.fbAuth?.currentUser?.email
    }

    fun getCurrUserUid(): String? {
        return repository.fbAuth?.currentUser?.uid
    }
    //여기까지



    fun bringKey(postKey: String) {
        Log.d("func","bringkey called")
        key = postKey
    }

    fun retriveKey(): String? {
        return key
    }

    fun bringCommentKey(commentKey: String) {
        cKey = commentKey
    }

    fun imageUpload(uri: Uri?) {
        uri?.let {
            repository.upLoadImage(it){url ->
                setImageUrl(url)
            }
        }
    }

    //박승진
    fun calDiffernce(date: String) : String {
        return try{
            val selectedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
            val today = LocalDate.now()
            val difference = today.until(selectedDate, ChronoUnit.DAYS).toInt()
            when{
                selectedDate.isBefore(today) -> "D+${abs(difference)}"
                selectedDate.isAfter(today) -> "D-$difference"
                else -> "D-day" }
        }catch (e: DateTimeParseException){
            "Error"
        }
    }

    fun setColor(color: Int){
        //val colorData = rgbTohex(color)
        _post.value = _post.value?.copy(
            color = color
        )
    }

    fun searchWord(word : String) {
        repository.searchWord(word)
    }


    fun observeSearchWord():LiveData<String>{
        repository.observeSearchWord(_searchWord)
        return searchWord
    }
    //여기까지
}