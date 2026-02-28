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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.Dictionary
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val getPhotosBySearchUseCase: GetPhotosBySearchUseCase
) : ViewModel() {
    private val _photosBySearch = MutableStateFlow<List<PhotosItem>>(emptyList())
    val photosBySearch: StateFlow<List<PhotosItem>> = _photosBySearch

    private val _searchQuery = MutableStateFlow("Ocean")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _collectionRow = MutableStateFlow(mapOf(_searchQuery.value to 1))
    private val _topKeys = MutableStateFlow(_collectionRow.value
        .toList()
        .sortedByDescending { it.second }
        .take(7)
        .map { it.first })
    val topKeys: StateFlow<List<String>> = _topKeys.asStateFlow()

    private val _selected = MutableStateFlow(_searchQuery.value)
    val selected: StateFlow<String> = _selected
    private val _stateLoading = MutableStateFlow(false)
    val stateLoading: StateFlow<Boolean> = _stateLoading
    private var searchJob: Job? = null

    init {
        startSearchCollector()
    }

    private fun startSearchCollector() {
        searchJob = viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collect { query ->
                    if (query.isNotEmpty()) loadPhotosBySearch(query)
                    val map = _collectionRow.value.toMutableMap()

                    map[query] = (map[query] ?: 0) + 1

                    _collectionRow.value = map
                    _topKeys.value = map
                        .toList()
                        .sortedByDescending { it.second }
                        .take(7)
                        .map { it.first }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSelectedChanged(item: String) {
        _selected.value = item
    }

    fun cancelCollector() {
        searchJob?.cancel()
        _searchQuery.value = ""
        startSearchCollector()
    }
    fun toggleBookmark(photo: PhotosItem) {

        _photosBySearch.value = _photosBySearch.value.map {

            if (it.title == photo.title) {
                it.copy(isSaved = photo.isSaved)
            } else {
                it
            }

        }
    }
    private fun loadPhotosBySearch(query: String) {
        viewModelScope.launch {
            try {
                _stateLoading.value = true
                _photosBySearch.value = getPhotosBySearchUseCase(query).map { it.toPhotosItem() }
            } catch (e: Exception) {
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
            } finally {
                _stateLoading.value = false
            }
        }
    }
}