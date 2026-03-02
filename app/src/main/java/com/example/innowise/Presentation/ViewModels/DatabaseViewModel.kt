package com.example.innowise.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innowise.Domain.UseCases.DeletePhotoUseCase
import com.example.innowise.Domain.UseCases.GetAllPhotosUseCase
import com.example.innowise.Domain.UseCases.InsertNewPhotoUseCase
import com.example.innowise.Domain.UseCases.UpdatePhotoUseCase
import com.example.innowise.Presentation.Mappers.toPhotos
import com.example.innowise.Presentation.Mappers.toPhotosItem
import com.example.innowise.Presentation.Models.PhotosItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.minus
import kotlin.collections.plus

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val insertNewPhotoUseCase: InsertNewPhotoUseCase,
    private val updatePhotoUseCase: UpdatePhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val getAllPhotosUseCase: GetAllPhotosUseCase,
) : ViewModel() {
    private val _photosFromDb = MutableStateFlow<List<PhotosItem>>(emptyList())
    val photosFromDb: StateFlow<List<PhotosItem>> = _photosFromDb

    private val _savedPhotosIds = MutableStateFlow<Set<String>>(emptySet())
    private val _stateLoadingDatabase = MutableStateFlow(false)
    val stateLoadingDatabase: StateFlow<Boolean> = _stateLoadingDatabase
    init {
        getAllPhotosFromDb()
    }

    fun insertNewPhoto(photo: PhotosItem) {
        viewModelScope.launch {
            insertNewPhotoUseCase(photo.toPhotos())
            getAllPhotosFromDb()
            _savedPhotosIds.value += photo.title
        }
    }
    fun deletePhoto(photo: PhotosItem) {
        viewModelScope.launch {
            deletePhotoUseCase(photo.toPhotos())
            getAllPhotosFromDb()
            _savedPhotosIds.value -= photo.title
        }
    }

    private fun getAllPhotosFromDb() {
        viewModelScope.launch {
            try {
                _stateLoadingDatabase.value = true
                _photosFromDb.value = getAllPhotosUseCase().map { it.toPhotosItem() }
                _savedPhotosIds.value = _photosFromDb.value.map { it.title }.toSet()
            }
            finally {
                _stateLoadingDatabase.value = false
            }
        }
    }
}