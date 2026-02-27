package com.example.innowise.Data.Mappers

import com.example.innowise.Data.Entity.DBO.PhotosDbModel
import com.example.innowise.Domain.Models.Photos


fun PhotosDbModel.toPhotos(): Photos = Photos(
    id = id,
    title = title,
    detailed = detailed,
    image = image,
    isSaved = isSaved
)