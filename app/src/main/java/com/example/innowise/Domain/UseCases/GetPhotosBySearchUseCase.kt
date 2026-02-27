package com.example.innowise.Domain.UseCases

import com.example.innowise.Domain.Interfaces.PhotosNetworkRepository
import com.example.innowise.Domain.Models.Photos

import javax.inject.Inject

class GetPhotosBySearchUseCase @Inject constructor(private val photosNetworkRepository: PhotosNetworkRepository) {
    suspend operator fun invoke(keyword: String): List<Photos>{
        return photosNetworkRepository.getPhotosBySearch(keyword)
    }
}