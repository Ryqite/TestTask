package com.example.innowise.Data.DataSource.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.innowise.Data.Entity.DBO.PhotosDbModel

@Dao
interface PhotosDao {
    @Insert
    suspend fun insertNewPhoto(photo: PhotosDbModel)
    @Update
    suspend fun updatePhoto(photo: PhotosDbModel)
    @Delete
    suspend fun deletePhoto(photo: PhotosDbModel)
    @Query("SELECT * FROM Photos")
    suspend fun getAllPhotos(): List<PhotosDbModel>
}