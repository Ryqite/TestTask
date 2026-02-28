package com.example.innowise.Domain.Models

data class Photos(
    val id: Int = 0,
    val author: String = "",
    val title: String = "",
    val image: String = "",
    val detailed: String = "",
    val isSaved: Boolean = false
)