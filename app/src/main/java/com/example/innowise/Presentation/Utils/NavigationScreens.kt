package com.example.week12.Presentation.Utils

import com.example.innowise.Presentation.Models.PhotosItem
import kotlinx.serialization.Serializable

sealed class NavigationScreens {
    @Serializable
    object HomeScreen
    @Serializable
    object BookmarksScreen
    @Serializable
    data class DetailsScreen(val photoId: String,
                             val from: String)
}