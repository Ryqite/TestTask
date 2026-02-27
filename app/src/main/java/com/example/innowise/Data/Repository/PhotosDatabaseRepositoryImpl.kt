package com.example.innowise.Data.Repository

import com.example.innowise.Data.DataSource.Local.LocalDataSource
import com.example.innowise.Data.Mappers.toPhotos
import com.example.innowise.Data.Mappers.toPhotosDbModel
import com.example.innowise.Domain.Interfaces.PhotosDatabaseRepository
import com.example.innowise.Domain.Models.Photos
import javax.inject.Inject

class PhotosDatabaseRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource):
    PhotosDatabaseRepository {
    override suspend fun insertNewPhoto(photo: Photos) {
        localDataSource.insertNewPhoto(photo.toPhotosDbModel())
    }

    override suspend fun updatePhoto(photo: Photos) {
        localDataSource.updatePhoto(photo.toPhotosDbModel())
    }

    override suspend fun deletePhoto(photo: Photos) {
        localDataSource.deletePhoto(photo.toPhotosDbModel())
    }

    override suspend fun getAllPhotos(): List<Photos> {
        val response = localDataSource.getAllPhotos()
        return response.map { it.toPhotos() }
    }
}