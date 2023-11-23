package com.example.teamprojectapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teamprojectapplication.viewmodel.Post
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


    fun observePost(): LiveData<MutableList<Post>> {
        val mutableData = MutableLiveData<MutableList<Post>>()
        postRef.addValueEventListener(object: ValueEventListener {
            val listData: MutableList<Post> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue(Post::class.java)

                        getData?. let {
                            listData.add(getData)
                        } ?: run {
                            // index가 null인 경우에 대한 처리
                            android.util.Log.e("PostRepository", "fail to get Value for the userSnapshot")
                        }


                        mutableData.value = listData
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return mutableData
    }
    fun userValue(index: String, newValue: String) {
        val index = userRef.push().key
        index?.let { nonNullableIndex ->
            userRef.child(nonNullableIndex).child("email").setValue(newValue)
        } ?: run {
            // index가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }


    }

    fun setPost() {
        //val post = Post("email", "writeId", "title", "text", "createAt", ArrayList(), ArrayList(), "date", "dday", 0, 0, true)
        postRef.child(fbAuth?.currentUser?.uid.toString()).setValue(Post())
        postRef.child(fbAuth?.currentUser?.uid.toString()).child("email").setValue(fbAuth?.currentUser?.email)
    }

    fun postValue(key: String, newValue: String) {
        postRef.child(fbAuth?.currentUser?.uid.toString()).child(key).setValue(newValue)
    }

    fun exPost(value: Post){
        postRef.child(fbAuth?.currentUser?.uid.toString()).setValue(value)
    }

    fun private(newValue: Boolean) {
        postRef.child(fbAuth?.currentUser?.uid.toString()).child("private").setValue(newValue)
    }

    /*fun postValue(key: String, newValue: String) {
        val index = postRef.push().key

// index가 null이 아닌 경우에만 코드 실행
        index?.let { nonNullableIndex ->
            val post = Post("email", "writeId", "title", "text", "createAt", ArrayList(), ArrayList(), "date", "dday", 0, 0, true)
            postRef.child(nonNullableIndex).setValue(post)
            postRef.child(nonNullableIndex).child(key).setValue(newValue)
        } ?: run {
            // index가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }
    }

     */
}