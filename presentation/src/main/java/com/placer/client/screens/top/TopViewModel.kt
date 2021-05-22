package com.placer.client.screens.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.UserView
import com.placer.client.entity.toViews
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.place.LoadPlacesUseCase
import com.placer.domain.usecase.user.LoadUsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class TopViewModel(
    private val loadUsersUseCase: LoadUsersUseCase = AppClass.appInstance.userComponent.loadUsersUseCase,
    private val loadPlacesUseCase: LoadPlacesUseCase = AppClass.appInstance.placeComponent.loadPlacesUseCase
) : BaseViewModel() {

    private var _topPlaces: MutableLiveData<List<Place>> = MutableLiveData()
    val topPlaces: LiveData<List<PlaceView>>
    get() = _topPlaces.map { it.toViews() }

    private var _topUsers: MutableLiveData<List<User>> = MutableLiveData()
    val topUsers: LiveData<List<UserView>>
    get() = _topUsers.map { it.toViews() }



    fun inputChanged(input: String){
        loadUsers(input)
        loadPlaces(input)
    }

    private fun loadUsers(input: String){
        viewModelScope.launch {
            val result = loadUsersUseCase.loadUsersByInputForTop(input).first()
            if (result.isSuccess){
                _topUsers.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    private fun loadPlaces(input: String){
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadPlacesByInputForTop(input).first()
            if (result.isSuccess){
                _topPlaces.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

}