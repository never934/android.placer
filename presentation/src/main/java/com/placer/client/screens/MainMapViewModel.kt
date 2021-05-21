package com.placer.client.screens

import androidx.lifecycle.*
import com.placer.client.Constants
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.util.Filters
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

internal class MainMapViewModel(private val placesUseCase: LoadPlacesUseCase) : BaseViewModel() {
    private var _searchPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val searchPlaces: LiveData<List<PlaceView>>
    get() = _searchPlaces.map { it.map { place -> place.toView() } }

    private var _mapPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val mapPlaces: LiveData<List<Place>>
    get() = Transformations.switchMap(mapFilter){ filter ->
        MutableLiveData(_mapPlaces.value?.filter { filter(it) })
    }

    private var _goToPlaceView: MutableLiveData<PlaceView?> = MutableLiveData()
    internal val goToPlaceView: LiveData<PlaceView?>
        get() = _goToPlaceView

    private var mapFilter: MutableLiveData<(place: Place) -> Boolean> = MutableLiveData()

    fun loadPlaces(input: String) {
        viewModelScope.launch {
            val result = placesUseCase.loadPlacesBySearchFromCacheWithEmptyFilter(input).first()
            if(result.isSuccess){
                _searchPlaces.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun showAllMapPoints(){
        mapFilter.value?.let {
            mapFilter.value = Filters::getAllPointsFilter
        }
    }

    fun showMyPoints(){
        mapFilter.value?.let {
            mapFilter.value = Filters::getMyPointsFilter
        }
    }

    fun placeClicked(place: PlaceView){
        _goToPlaceView.value = place
    }

    fun placeClicked(placeId: String?){
        placeId?.let {
            _goToPlaceView.value = _mapPlaces.value?.first { it.id == placeId }?.toView()
        }
    }

    fun navigatedToPlaceView(){
        _goToPlaceView.value = null
    }

    fun loadMapPlaces() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = placesUseCase.loadPlaces().single()
            isRefreshing.value = false
            if (result.isSuccess){
                _mapPlaces.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            mapFilter.value = Filters::getAllPointsFilter
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