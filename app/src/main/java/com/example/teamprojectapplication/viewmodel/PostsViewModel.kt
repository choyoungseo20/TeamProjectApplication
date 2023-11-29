package com.example.teamprojectapplication.viewmodel

import android.os.Build
import android.view.animation.Transformation
import androidx.annotation.RequiresApi
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.format.DateTimeParseException

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

    var key: String? = null

    fun bringKey(postKey: String) {
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

    fun bringEmail(key: String, callback: (String?)-> Unit) : String {
        val email : String
        email = repository.bringEmail(key, callback).toString()
        return email
    }

    fun bringText(key: String, callback: (String?)-> Unit) : String {
        val text : String
        text = repository.bringText(key, callback).toString()
        return text
    }

    fun getPostDataByKey(key: String): LiveData<Post> {

        val postData = MutableLiveData<Post>()

        val databaseReference = FirebaseDatabase.getInstance().reference.child("post").child(key)

        if(databaseReference != null ) {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val post = dataSnapshot.getValue(Post::class.java)
                    postData.value = post!!
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

        return postData
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

    fun setColor(newValue: Int) {
        val hexRed = newValue.red.toString(16).padStart(2, '0')
        val hexGreen = newValue.green.toString(16).padStart(2, '0')
        val hexBlue = newValue.blue.toString(16).padStart(2, '0')
        repository.postValue("color", "#$hexRed$hexGreen$hexBlue")
    }

     */
}