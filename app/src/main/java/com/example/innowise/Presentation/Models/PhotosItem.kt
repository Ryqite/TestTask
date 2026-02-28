package com.example.innowise.Presentation.Models

import kotlinx.serialization.Serializable
@Serializable
data class PhotosItem(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val image: String = "",
    val detailed: String = "",
    val isSaved: Boolean = false
)