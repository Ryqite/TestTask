package com.example.innowise.Data.Entity.DBO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photos")
data class PhotosDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "imageLink")
    val image: String = "",
    @ColumnInfo(name = "detailedImageLink")
    val detailed: String = "",
    @ColumnInfo(name = "Saved")
    val isSaved: Boolean = false
)