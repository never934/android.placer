package com.placer.client.screens.places.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toViews
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class PlaceChoosePointViewModel(
    private val loadPlacesUseCase: LoadPlacesUseCase = AppClass.appInstance.placeComponent.loadPlacesUseCase
) : BaseViewModel() {

    private var _mapPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    val mapPlaces: LiveData<List<PlaceView>>
    get() = _mapPlaces.map { it.toViews() }

    private var _pointChosen: MutableLiveData<LatLng?> = MutableLiveData()
    val pointChosen: LiveData<LatLng?>
    get() = _pointChosen

    var firstStart = true

    init {
        loadPlaces()
    }

    fun pointChosen(latLng: LatLng) {
        _pointChosen.value = latLng
    }

    fun pointChosenUsed() {
        _pointChosen.value = null
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadPlaces().first()
            if (result.isSuccess) {
                _mapPlaces.value = result.getOrNull()
            }
        }
    }

}