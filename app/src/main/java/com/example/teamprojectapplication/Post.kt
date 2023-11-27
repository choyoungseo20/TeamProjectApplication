package com.example.teamprojectapplication

data class Post(
    val email: String = "",
    val title: String = "",
    val text: String = "",
    val likeList: ArrayList<String> = ArrayList(),
    val imgList: ArrayList<String> = ArrayList(),
    val date: String = "",
    val dday: String = "",
    val like: Int = 0,
    val comment: Int = 0,
    val private: Boolean = false,
    val color: String = ""
)