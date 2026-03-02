package com.example.innowise.Domain.UseCases

import com.example.innowise.Domain.Interfaces.PhotosNetworkRepository
import com.example.innowise.Domain.Models.Photos

import javax.inject.Inject

class GetPopularPhotosUseCase @Inject constructor(private val photosNetworkRepository: PhotosNetworkRepository) {
    suspend operator fun invoke(): List<Photos>{
        return photosNetworkRepository.getPopularPhotos()
    }
}