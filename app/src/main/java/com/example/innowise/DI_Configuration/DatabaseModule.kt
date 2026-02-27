package com.example.innowise.DI_Configuration

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.innowise.Data.DataSource.Local.LocalDataSource
import com.example.innowise.Data.DataSource.Local.LocalDataSourceImpl
import com.example.innowise.Data.DataSource.Local.PhotosDatabase
import com.example.innowise.Data.DataSource.Local.PhotosDao
import com.example.innowise.Data.Repository.PhotosDatabaseRepositoryImpl
import com.example.innowise.Domain.Interfaces.PhotosDatabaseRepository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule{
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}
@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseRepository{
    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(impl: PhotosDatabaseRepositoryImpl): PhotosDatabaseRepository
}
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun provideBooksDatabase(@ApplicationContext context: Context): PhotosDatabase{
        return Room.databaseBuilder(
                context,
            PhotosDatabase::class.java,
                "PhotosDatabase"
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideBooksDao(photosDatabase: PhotosDatabase): PhotosDao{
        return photosDatabase.photosDao()
    }
}