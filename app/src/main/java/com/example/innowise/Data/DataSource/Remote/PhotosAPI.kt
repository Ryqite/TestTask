package com.example.innowise.Data.DataSource.Remote

import com.example.innowise.Data.Entity.DTO.PhotosDTO
import com.example.innowise.Data.Utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotosAPI {
    @GET("v1/search")
    @Headers("Authorization: um2qaU7BvikbdWthAywU6LReOPDAY0UJCTRqIYpUK2xu7dBARtdgfa61")
    suspend fun getPhotosBySearch(
        @Query("query")
        keywords: String = "Ocean",
        @Query("page")
        page: Int = 1
    ): PhotosDTO
}