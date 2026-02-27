package com.example.innowise.Data.Mappers
import com.example.innowise.Data.Entity.DBO.PhotosDbModel
import com.example.innowise.Domain.Models.Photos

fun Photos.toPhotosDbModel(): PhotosDbModel = PhotosDbModel(
    id = id,
    title = title,
    image = image,
    detailed = detailed,
    isSaved = isSaved
)