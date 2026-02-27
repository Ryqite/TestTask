package com.example.innowise.DI_Configuration

import com.example.innowise.Data.DataSource.Remote.PhotosAPI
import com.example.innowise.Data.DataSource.Remote.RemoteDataSource
import com.example.innowise.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.innowise.Data.Repository.PhotosNetworkRepositoryImpl
import com.example.innowise.Data.Utils.Constants.Companion.BASE_URL
import com.example.innowise.Domain.Interfaces.PhotosNetworkRepository
import com.example.week12.Data.DataSource.Remote.RetryInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule{
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}
@Module
@InstallIn(SingletonComponent::class)
abstract class BooksRepository{
    @Binds
    @Singleton
    abstract fun bindBooksRepository(impl: PhotosNetworkRepositoryImpl): PhotosNetworkRepository
}
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
    return OkHttpClient.Builder()
        .addInterceptor(RetryInterceptor(3))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =HttpLoggingInterceptor.Level.BODY
        }).build()
    }
    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideBooksAPI(retrofit: Retrofit): PhotosAPI {
        return retrofit.create(PhotosAPI::class.java)
    }
}