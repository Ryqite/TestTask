package com.example.innowise.Presentation.Mappers

import com.example.innowise.Domain.Models.Photos
import com.example.innowise.Presentation.Models.PhotosItem

fun Photos.toPhotosItem(): PhotosItem = PhotosItem(
    id = id,
    title = title,
    author = author,
    image = image,
    detailed = detailed,
    isSaved = isSaved
)