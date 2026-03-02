package com.example.innowise.Domain.Interfaces

import com.example.innowise.Domain.Models.Photos

interface PhotosNetworkRepository {
   suspend fun getPhotosBySearch(keyword:String):List<Photos>
   suspend fun getPopularPhotos():List<Photos>
}