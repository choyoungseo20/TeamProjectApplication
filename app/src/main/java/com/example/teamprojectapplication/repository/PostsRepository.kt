package com.example.teamprojectapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.teamprojectapplication.Post
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.values
import com.google.firebase.storage.storage
import java.security.Key
import java.text.SimpleDateFormat
import java.util.Date


class PostsRepository() {
    val database get() = Firebase.database
    val userRef get() = database.getReference("user")
    val postRef get() = database.getReference("post")
    val fbAuth get() = Firebase.auth
    //var index: String? = null


    val storage get() = Firebase.storage
    val storageRef get() = storage.getReference("image")
    val fileName get() = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
    val mountainsRef get() = storageRef.child("${fileName}.png")



    fun observePost(post: MutableLiveData<MutableList<Post>>) {
        postRef.addValueEventListener(object: ValueEventListener { //콜백함수
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
    fun getCurrUserEmail() : String {
        return fbAuth?.currentUser?.email.toString()
    }



    fun setPost(post: Post?){
        val postKey = postRef.push().key
        postKey?.let { nonNullableIndex ->
            val updatedPost = post?.copy(key = nonNullableIndex)
            postRef.child(nonNullableIndex).setValue(updatedPost)
            postRef.child(nonNullableIndex).child("email").setValue(fbAuth?.currentUser?.email)
        } ?: run {
            // postKey가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the post.")
        }
    }
    fun addElem(postKey: String, child: String, newValue: String) {
        postRef.child(postKey).child(child).setValue(newValue)
    }

    fun bringContent(postKey: String, child: String, callback: (String?) -> Unit)  {
        val postRef2 = FirebaseDatabase.getInstance().reference.child("post").child(postKey)
        postRef2.child(child).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val content = dataSnapshot.getValue(String::class.java)
                callback(content)
            }

            override fun onCancelled(error: DatabaseError) {

                callback(null)
            }
        })
    }

    private  val likedMap: MutableMap<String, MutableMap<String,Boolean>> = mutableMapOf()
    fun likePost(postKey: String, userId: String) {
        val postLikesRef = postRef.child(postKey).child("likes")
        postLikesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val likesMap: MutableMap<String, Boolean> = if (snapshot.exists()) {
                    snapshot.value as MutableMap<String, Boolean>
                } else {
                    HashMap()
                }

                // 사용자가 이미 게시물에 좋아요를 눌렀는지 확인
                if (likesMap.containsKey(userId)) {
                    // 사용자가 이미 좋아요를 눌렀다면 좋아요 제거
                    likesMap.remove(userId)
                } else {
                    // 사용자가 좋아요를 누르지 않았다면 좋아요 추가
                    likesMap[userId] = true
                }

                // 좋아요 수 업데이트
                postRef.child(postKey).child("likeCount").setValue(likesMap.size)
                // 좋아요 맵 업데이트
                postLikesRef.setValue(likesMap)


            }

            override fun onCancelled(error: DatabaseError) {
                // 오류 처리
            }
        })
    }

}