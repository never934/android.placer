package com.placer.client.screens.places

import androidx.lifecycle.*
import com.placer.client.Constants
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.util.Filters
import com.placer.domain.entity.place.Place
import com.placer.domain.usecase.place.LoadPlacesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlacesViewModel(private val placesUseCase: LoadPlacesUseCase) : BaseViewModel(){

    private var _places: MutableLiveData<List<Place>> = MutableLiveData(arrayListOf())
    internal val places: LiveData<List<PlaceView>>
    get() = Transformations.switchMap(_placesFilter){ filter ->
        MutableLiveData(_places.value?.filter { filter(it) }?.map { it.toView() })
    }

    private var _goToPlaceView: MutableLiveData<PlaceView?> = MutableLiveData()
    internal val goToPlaceView: LiveData<PlaceView?>
    get() = _goToPlaceView

    private var _placesFilter: MutableLiveData<(place: Place) -> Boolean> = MutableLiveData(Filters::getAllPointsFilter)
    private var _tabPosition: MutableLiveData<Int> = MutableLiveData(Constants.ALL_TAB_POSITION)


    init {
        loadPlaces()
    }

    fun loadPlaces() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = placesUseCase.loadPlaces().first()
            isRefreshing.value = false
            if (result.isSuccess){
                _places.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            setFilter()
        }
    }

    fun loadPlacesByInput(input: String) {
        viewModelScope.launch {
            val result = placesUseCase.loadPlacesBySearchFromCache(input).first()
            if (result.isSuccess){
                _places.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            setFilter()
        }
    }

    internal fun placeClicked(place: PlaceView){
        _goToPlaceView.value = place
    }

    fun tabPositionChanged(position: Int){
        _tabPosition.value = position
        setFilter()
    }

    fun navigatedToPlaceView(){
        _goToPlaceView.value = null
    }

    private fun setFilter(){
        when(_tabPosition.value){
            Constants.ALL_TAB_POSITION -> _placesFilter.value = Filters::getAllPointsFilter
            Constants.MY_TAB_POSITION -> _placesFilter.value = Filters::getMyPointsFilter
        }
    }

    class Factory(private val placesUseCase: LoadPlacesUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlacesViewModel(placesUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}