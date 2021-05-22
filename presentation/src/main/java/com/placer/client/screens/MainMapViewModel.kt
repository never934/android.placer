package com.placer.client.screens

import androidx.lifecycle.*
import com.google.android.gms.maps.model.Marker
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.util.Filters
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

internal class MainMapViewModel constructor(
    private val placesUseCase: LoadPlacesUseCase = AppClass.appInstance.placeComponent.loadPlacesUseCase
    ) : BaseViewModel() {

    private var _searchPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val searchPlaces: LiveData<List<PlaceView>>
    get() = _searchPlaces.map { it.map { place -> place.toView() } }

    private var _mapPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    val mapPlaces: LiveData<List<Place>>
    get() = Transformations.switchMap(mapFilter){ filter ->
        MutableLiveData(_mapPlaces.value?.filter { filter(it) })
    }

    private var _mapMarkers: ArrayList<Marker> = arrayListOf()
    val mapMarkers: List<Marker>
    get() = _mapMarkers

    private var _goToPlaceView: MutableLiveData<PlaceView?> = MutableLiveData()
    val goToPlaceView: LiveData<PlaceView?>
    get() = _goToPlaceView

    private var mapFilter: MutableLiveData<(place: Place) -> Boolean> = MutableLiveData()

    fun loadPlaces(input: String) {
        viewModelScope.launch {
            val result = placesUseCase.loadPlacesByInputFromCacheWithEmptyFilter(input).first()
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

    fun updateMapMarkers(markers: List<Marker>){
        _mapMarkers.clear()
        _mapMarkers.addAll(markers)
    }
}