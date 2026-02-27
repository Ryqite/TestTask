package com.example.innowise.Presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innowise.Domain.UseCases.GetPhotosBySearchUseCase
import com.example.innowise.Presentation.Mappers.toPhotosItem
import com.example.innowise.Presentation.Models.PhotosItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
@OptIn(FlowPreview::class)
@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val getPhotosBySearchUseCase: GetPhotosBySearchUseCase
) : ViewModel() {
    private val _PhotosBySearch = MutableStateFlow<List<PhotosItem>>(emptyList())
    val photosBySearch: StateFlow<List<PhotosItem>> = _PhotosBySearch

    private val _searchQuery = MutableStateFlow("Romantic")
    val searchQuery: StateFlow<String> = _searchQuery

    private var searchJob: Job? = null

    init {
        startSearchCollector()
    }

    private fun startSearchCollector(){
        searchJob = viewModelScope.launch {
            _searchQuery
                .debounce(800)
                .distinctUntilChanged()
                .collect{query->
                    if(query.isNotEmpty()) loadPhotosBySearch(query)
                }
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    fun cancelCollector(){
        searchJob?.cancel()
        _searchQuery.value = ""
        startSearchCollector()
    }
    private fun loadPhotosBySearch(query: String) {
        try {
            viewModelScope.launch {
                _PhotosBySearch.value = getPhotosBySearchUseCase(query).map { it.toPhotosItem() }
            }
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun handleError(e: Exception) {
        when (e) {
            is IOException -> Log.e("ErrorHandler", "Ошибка сети: ${e.message}")
            is HttpException -> when (e.code()) {
                401 -> Log.e("ErrorHandler", "Требуется авторизация")
                403 -> Log.e("ErrorHandler", "Доступ запрещен")
                404 -> Log.e("ErrorHandler", "Новости не найдены")
                in 500..599 -> Log.e("ErrorHandler", "Ошибка сервера")
                else -> Log.e("ErrorHandler", "HTTP ошибка: ${e.code()}")
            }

            else -> Log.e("ErrorHandler", "Неизвестная ошибка: ${e.message}")
        }
    }
}