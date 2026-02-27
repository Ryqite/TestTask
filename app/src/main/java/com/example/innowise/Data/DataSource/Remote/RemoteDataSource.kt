package com.example.innowise.Data.DataSource.Remote

import com.example.innowise.Data.Entity.DTO.PhotosDTO

interface RemoteDataSource {
    suspend fun getPhotosBySearch(keyword: String): PhotosDTO
}