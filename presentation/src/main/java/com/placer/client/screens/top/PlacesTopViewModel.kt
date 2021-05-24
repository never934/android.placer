package com.placer.client.screens.top

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toViews
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class PlacesTopViewModel(
    private val loadPlacesUseCase: LoadPlacesUseCase = AppClass.appInstance.placeComponent.loadPlacesUseCase
) : BaseViewModel() {

    private var _topPlaces: MutableLiveData<List<Place>> = MutableLiveData()
    val topPlaces: LiveData<List<PlaceView>>
    get() = _topPlaces.map { it.toViews() }

    init {
        reload()
    }

    fun reload() {
        isRefreshing.value = true
        viewModelScope.launch {
            loadPlaces()
            isRefreshing.value = false
        }
    }

    private suspend fun loadPlaces() {
        val result = loadPlacesUseCase.loadPlacesForTop().first()
        if(result.isSuccess){
            _topPlaces.value = result.getOrNull()
        }else{
            showSnackBar.value = result.exceptionOrNull()?.message
        }
    }

    fun loadPlaces(input: String) {
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadPlacesByInputForTop(input).first()
            if (result.isSuccess){
                _topPlaces.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun placeClicked(place: PlaceView) {
        Log.e("clicked", "place ${place.name}")
    }

}