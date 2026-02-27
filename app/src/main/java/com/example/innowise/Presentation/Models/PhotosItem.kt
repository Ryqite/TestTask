package com.example.innowise.Presentation.Models

data class PhotosItem(
    val id: Int = 0,
    val title: String = "",
    val image: String = "",
    val detailed: String = "",
    val isSaved: Boolean = false
)
