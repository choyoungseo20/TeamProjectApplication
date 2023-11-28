package com.example.teamprojectapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teamprojectapplication.Post
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class PostsRepository() {
    val database = Firebase.database
    val userRef = database.getReference("user")
    val postRef = database.getReference("post")
    val fbAuth = Firebase.auth
    var index: String? = null


    fun observePost(post: MutableLiveData<MutableList<Post>>) {
        postRef.addValueEventListener(object: ValueEventListener {
            val listData: MutableList<Post> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    listData.clear()
                    for (userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue(Post::class.java)

                        getData?.let {
                            listData.add(getData)
                        } ?: run {
                            // index가 null인 경우에 대한 처리
                            Log.e("PostRepository", "fail to get Value for the userSnapshot")
                        }
                        post.value = listData
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setUser() {
        val userId = fbAuth?.currentUser?.uid
        userId?.let { uid ->
            userRef.child(uid).setValue(fbAuth?.currentUser?.email)
        }
    }
    fun findKey() {
        //this.index =
    }
    fun setPost() {
        this.index = postRef.push().key
        val post = Post("email", "title", "text", "date", "dday", 0, HashMap(),0, true, "#FFFFFF","key")
        index?.let { nonNullableIndex ->
            postRef.child(nonNullableIndex).setValue(post)
            postRef.child(nonNullableIndex).child("email").setValue(fbAuth?.currentUser?.email)
            postRef.child(nonNullableIndex).child("key").setValue(nonNullableIndex)
        } ?: run {
            // index가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }

    }

    fun postValue(key: String, newValue: String) {
        index?.let { nonNullableIndex ->
            postRef.child(nonNullableIndex).child(key).setValue(newValue)
        } ?: run {
            // index가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }
    }

    fun private(newValue: Boolean) {
        index?.let { nonNullableIndex ->
            postRef.child(nonNullableIndex).child("private").setValue(newValue)
        } ?: run {
            // index가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }

    }

    fun exPost(updatedPosts: List<Post>){
        index?.let { nonNullableIndex ->
            for (newValue in updatedPosts) {
                postRef.child(nonNullableIndex).setValue(newValue)
            }

        } ?: run {
            // index가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }
    }

}