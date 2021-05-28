package com.placer.client.screens.city

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.placer.client.AppClass
import com.placer.client.Constants
import com.placer.client.base.BaseViewModel
import com.placer.client.servicelocator.ServiceLocator
import com.placer.data.AppPrefs
import com.placer.domain.entity.city.City
import com.placer.domain.usecase.city.LoadCitiesUseCase
import com.placer.domain.usecase.user.UpdateUserCityUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class ChooseCityViewModel(
    private val loadCitiesUseCase: LoadCitiesUseCase = ServiceLocator.instance.cityComponent.loadCitiesUseCase,
    private val updateUserCityUseCase: UpdateUserCityUseCase = ServiceLocator.instance.userComponent.updateUserCityUseCase
) : BaseViewModel() {

    private var _cities: MutableLiveData<List<City>> = MutableLiveData(arrayListOf())
    internal val cities: LiveData<List<City>>
    get() = _cities

    private var _cityUpdated: MutableLiveData<Boolean?> = MutableLiveData()
    internal val cityUpdated: LiveData<Boolean?>
        get() = _cityUpdated

    private var searchJob: Job? = null

    init {
        loadCities("")
    }

    fun loadCities(input: String) {
        isRefreshing.value = true
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(Constants.CITIES_REQUEST_DELAY)
            val result = loadCitiesUseCase.loadCities(input).first()
            if (result.isSuccess){
                _cities.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isRefreshing.value = false
        }
    }

    fun cityChosen(city: City) {
        isLoading.value = true
        viewModelScope.launch {
            val result = updateUserCityUseCase.updateUserCity(AppPrefs.getUserId(), city).first()
            if (result.isSuccess){
                _cityUpdated.value = true
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }

    fun cityUpdatedTransactionExecuted() {
        _cityUpdated.value = null
    }
}