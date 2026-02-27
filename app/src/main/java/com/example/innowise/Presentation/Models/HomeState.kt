package com.example.innowise.Presentation.Models

data class HomeState(

    val query: String = "",

    val collections: List<String> = emptyList(),

    val photos: List<PhotosItem> = emptyList(),

    val isLoading: Boolean = false
)
