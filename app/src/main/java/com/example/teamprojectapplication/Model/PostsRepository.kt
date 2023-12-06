package com.example.teamprojectapplication.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import java.util.Date


class PostsRepository() {
    val database get() = Firebase.database
    val userRef get() = database.getReference("user")
    val postRef get() = database.getReference("post")
    val searchRef get() = database.getReference("search")
    val fbAuth get() = Firebase.auth



    val storage get() = Firebase.storage
    val storageRef get() = storage.getReference("image")
    val fileName get() = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
    val mountainsRef get() = storageRef.child("${fileName}.png")
    /*
    fun imageUpload(uri: Uri) {
        val uploadTask = mountainsRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            //Toast.makeText(AddDiaryFragment(), "사진 업로드 성공", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            //Toast.makeText(MainActivity(), "사진 업로드 실패", Toast.LENGTH_SHORT).show()
        }
    }


    private fun imageDownload() {
        val downloadTask = mountainsRef.downloadUrl
        downloadTask.addOnSuccessListener { uri ->

        }.addOnFailureListener {

        }
    }

     */



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

    fun addComment(postKey: String, comment: Post.Comment) {
        val commentsRef = postRef.child(postKey).child("comments")
        val commentKey = commentsRef.push().key
        commentKey?.let {nonNullableIndex ->
            commentsRef.child(nonNullableIndex).setValue(comment)
            commentsRef.child(nonNullableIndex).child("commentKey").setValue(nonNullableIndex)
        } ?: run {
            // postKey가 null인 경우에 대한 처리
            Log.e("PostViewModel", "Failed to generate a key for the comment.")
        }
    }

    fun updateCommentCount(postKey: String, count: Int) {
        postRef.child(postKey).child("commentCount").setValue(count)
    }


    fun observeComments(postKey: String, comments: MutableLiveData<MutableList<Post.Comment>>) {
        val commentsRef = postRef.child(postKey).child("comments")
        commentsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val commentsList = mutableListOf<Post.Comment>()
                for (commentSnapshot in snapshot.children) {
                    val comment = commentSnapshot.getValue(Post.Comment::class.java)
                    comment?.let {
                        commentsList.add(it)
                    }
                }
                comments.value = commentsList
            }

            override fun onCancelled(error: DatabaseError) {
                // 오류 처리
            }
        })
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

    fun bringContent(postKey: String, child: String, callback: (String?) -> Unit)  {
        val postRef2 = FirebaseDatabase.getInstance().reference.child("post").child(postKey)
        postRef2.child(child).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val content = dataSnapshot.getValue(String::class.java)
                callback(content)
            }

            override fun onCancelled(error: DatabaseError) {

                callback(null)
            }
        })
    }

    fun deletePost(postKey: String) {
        postRef.child(postKey).removeValue()
    }

    fun deleteComment(postKey: String, comment: Post.Comment) {
        val commentRef = postRef.child(postKey).child("comments").child(comment.commentKey!!)
        commentRef.removeValue()
    }

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
    fun observeLikeStatus(postKey: String, userId: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        val postLikesRef = postRef.child(postKey).child("likes")

        postLikesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val likesMap: MutableMap<String, Boolean> = if (snapshot.exists()) {
                    snapshot.value as MutableMap<String, Boolean>
                } else {
                    HashMap()
                }

                // 현재 사용자의 좋아요 상태 확인
                val currentUserLiked = likesMap.containsKey(userId)

                // LiveData에 값 업데이트
                resultLiveData.postValue(currentUserLiked)
            }

            override fun onCancelled(error: DatabaseError) {
                // 오류 처리
            }
        })

        return resultLiveData
    }

    fun searchWord(word : String){
        val userId = fbAuth?.currentUser?.uid
        userId?.let {
            searchRef.child(it).setValue(word)
        }
    }

    fun observeSearchWord(word : MutableLiveData<String>){
        val userId = fbAuth.currentUser?.uid
        userId?.let {
            searchRef.child(userId).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    word.postValue(snapshot.getValue(String::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

}