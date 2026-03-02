package com.example.innowise.Data.Repository

import com.example.innowise.Data.DataSource.Remote.RemoteDataSource
import com.example.innowise.Data.Mappers.toPhotos
import com.example.innowise.Domain.Interfaces.PhotosNetworkRepository
import com.example.innowise.Domain.Models.Photos
import javax.inject.Inject

class PhotosNetworkRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):
    PhotosNetworkRepository {
    override suspend fun getPhotosBySearch(keyword: String): List<Photos> {
        val response = remoteDataSource.getPhotosBySearch(keyword)
        return response.photos.map {it.toPhotos()
        }
    }
    override suspend fun getPopularPhotos(): List<Photos> {
        val response = remoteDataSource.getPopularPhotos()
        return response.photos.map {it.toPhotos()
        }
    }
}