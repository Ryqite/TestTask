package com.example.innowise.Data.Entity.DTO

data class PhotosDTO(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)