package com.example.innowise.Data.DataSource.Remote

import com.example.innowise.Data.Entity.DTO.PhotosDTO
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(private val photosAPI: PhotosAPI): RemoteDataSource {
    override suspend fun getPhotosBySearch(keyword: String): PhotosDTO {
       return photosAPI.getPhotosBySearch(keywords = keyword)
    }
    override suspend fun getPopularPhotos(): PhotosDTO {
        return photosAPI.getPhotosBySearch()
    }
}