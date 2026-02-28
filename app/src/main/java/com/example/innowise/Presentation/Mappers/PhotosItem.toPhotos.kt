package com.example.innowise.Presentation.Mappers

import com.example.innowise.Domain.Models.Photos
import com.example.innowise.Presentation.Models.PhotosItem

fun PhotosItem.toPhotos(): Photos = Photos(
    id = id,
    title = title,
    author = author,
    image = image,
    detailed = detailed,
    isSaved = isSaved
)