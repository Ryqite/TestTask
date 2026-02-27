package com.example.innowise.Data.Mappers

import com.example.innowise.Data.Entity.DTO.Photo
import com.example.innowise.Domain.Models.Photos


fun Photo.toPhotos(): Photos = Photos(
    id = 0,
    title = alt?:"",
    image = url?:"",
    detailed = src.landscape?:"",
    isSaved = false
)