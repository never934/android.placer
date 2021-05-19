package com.placer.client.screens

import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainMapViewModel(private val placesUseCase: LoadPlacesUseCase) : BaseViewModel() {
    private var _searchPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val searchPlaces: LiveData<List<PlaceView>>
    get() = _searchPlaces.map { it.map { place -> place.toView() } }

    private var _mapPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val mapPlaces: LiveData<List<Place>>
    get() = _mapPlaces

    init {
        loadMapPlaces()
    }

    fun loadPlaces(input: String) {
        viewModelScope.launch {
            val result = placesUseCase.loadPlacesBySearch(input).first()
            if(result.isSuccess){
                _searchPlaces.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    private fun loadMapPlaces() {
        viewModelScope.launch {
            val result = placesUseCase.loadPlaces().first()
            if (result.isSuccess){
                _mapPlaces.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    class Factory(private val placesUseCase: LoadPlacesUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainMapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainMapViewModel(placesUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}