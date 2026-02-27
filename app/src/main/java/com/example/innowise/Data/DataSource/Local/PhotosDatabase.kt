package com.example.innowise.Data.DataSource.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.innowise.Data.Entity.DBO.PhotosDbModel

@Database(entities = [PhotosDbModel::class], version = 1)
abstract class PhotosDatabase: RoomDatabase() {
   abstract fun photosDao(): PhotosDao
}