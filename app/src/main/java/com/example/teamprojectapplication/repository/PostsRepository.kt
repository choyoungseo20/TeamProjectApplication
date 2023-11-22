package com.example.teamprojectapplication.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database


class PostsRepository() {
    val database = Firebase.database
    val userRef = database.getReference("user")
    val postRef = database.getReference("post")

    private fun userValue(num: String, newValue: String) {
        userRef.child(num).child("email").setValue(newValue)
    }

    private fun postValue(num: String, key: String, newValue: String) {
        postRef.child(num).child(key).setValue(newValue)
    }
}