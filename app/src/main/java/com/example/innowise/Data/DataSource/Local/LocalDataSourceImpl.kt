package com.example.innowise.Data.DataSource.Local

import com.example.innowise.Data.Entity.DBO.PhotosDbModel
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val photosDao: PhotosDao): LocalDataSource {
    override suspend fun insertNewPhoto(photo: PhotosDbModel) {
        photosDao.insertNewPhoto(photo)
    }

    override suspend fun updatePhoto(photo: PhotosDbModel) {
        photosDao.updatePhoto(photo)
    }

    override suspend fun deletePhoto(photo: PhotosDbModel) {
        photosDao.deletePhoto(photo)
    }

    override suspend fun getAllPhotos(): List<PhotosDbModel> {
        return photosDao.getAllPhotos()
    }
}