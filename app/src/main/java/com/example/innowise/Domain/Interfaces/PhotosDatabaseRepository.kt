package com.example.innowise.Domain.Interfaces

import com.example.innowise.Domain.Models.Photos


interface PhotosDatabaseRepository {
    suspend fun insertNewPhoto(book: Photos)
    suspend fun updatePhoto(book: Photos)
    suspend fun deletePhoto(book: Photos)
    suspend fun getAllPhotos(): List<Photos>
}