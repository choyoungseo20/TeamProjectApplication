package com.example.teamprojectapplication

data class Post(
    val email: String = "",
    val title: String = "",
    val text: String = "",
    val date: String = "",
    val dday: String = "",
    val imageUrl: String = "",
    val likeCount: Int = 0,
    val likes: MutableMap<String, Boolean> = HashMap(),
    val commentCount: Int = 0,
    val private: Boolean = false,
    val color: String = "",
    val key: String =""
) {
    data class Comment(
        val uid: String = "",
        val userId: String = "",
        val content: String = "",
        val timeStamp: Long? = null

    )
}
