package com.example.innowise.Domain.UseCases

import com.example.innowise.Domain.Interfaces.PhotosDatabaseRepository
import com.example.innowise.Domain.Models.Photos
import javax.inject.Inject

class GetAllPhotosUseCase @Inject constructor(private val photosDatabaseRepository: PhotosDatabaseRepository) {
    suspend operator fun invoke(): List<Photos> {
      return photosDatabaseRepository.getAllPhotos()
    }
}