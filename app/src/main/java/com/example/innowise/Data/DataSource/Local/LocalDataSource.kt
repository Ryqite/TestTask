package com.example.innowise.Data.DataSource.Local

import com.example.innowise.Data.Entity.DBO.PhotosDbModel

interface LocalDataSource {

    suspend fun insertNewPhoto(photo: PhotosDbModel)

    suspend fun updatePhoto(photo: PhotosDbModel)

    suspend fun deletePhoto(photo: PhotosDbModel)

    suspend fun getAllPhotos(): List<PhotosDbModel>

}