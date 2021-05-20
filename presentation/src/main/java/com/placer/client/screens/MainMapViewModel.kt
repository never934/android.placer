package com.placer.client.screens

import androidx.lifecycle.*
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.util.Filters
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class MainMapViewModel(private val placesUseCase: LoadPlacesUseCase) : BaseViewModel() {
    private var _searchPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val searchPlaces: LiveData<List<PlaceView>>
    get() = _searchPlaces.map { it.map { place -> place.toView() } }

    private var _mapPlaces: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val mapPlaces: LiveData<List<Place>>
    get() = Transformations.switchMap(mapFilter){ filter ->
        MutableLiveData(_mapPlaces.value?.filter { filter(it) })
    }

    private var mapFilter: MutableLiveData<(place: Place) -> Boolean> = MutableLiveData(Filters::getAllMapPointsFilter)

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

    fun showAllMapPoints(){
        mapFilter.value = Filters::getAllMapPointsFilter
    }

    fun showMyPoints(){
        mapFilter.value = Filters::getMyPointsFilter
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