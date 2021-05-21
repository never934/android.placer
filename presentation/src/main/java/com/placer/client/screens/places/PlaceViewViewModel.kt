package com.placer.client.screens.places

import androidx.lifecycle.*
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.data.AppPrefs
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class PlaceViewViewModel(private val loadPlacesUseCase: LoadPlacesUseCase, private val placeId: String) : BaseViewModel() {
    private var _place: MutableLiveData<Place> = MutableLiveData()
    val place: LiveData<PlaceView>
    get() = _place.map { it.toView() }

    private var _clientIsPlaceAuthor: MutableLiveData<Boolean> = MutableLiveData()
    val clientIsPlaceAuthor: LiveData<Boolean>
    get() = _clientIsPlaceAuthor

    init {
        loadPlace()
    }

    fun loadPlace() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadPlaceById(placeId).first()
            if(result.isSuccess){
                _place.value = result.getOrNull()
                _clientIsPlaceAuthor.value = _place.value?.author?.id == AppPrefs.getUserId()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isRefreshing.value = false
        }
    }

    class Factory(private val placesUseCase: LoadPlacesUseCase, private val placeId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaceViewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlaceViewViewModel(placesUseCase, placeId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}