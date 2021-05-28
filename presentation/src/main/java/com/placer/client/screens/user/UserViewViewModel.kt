package com.placer.client.screens.user

import androidx.lifecycle.*
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.*
import com.placer.client.entity.PlaceView
import com.placer.client.entity.UserView
import com.placer.client.entity.toView
import com.placer.client.servicelocator.ServiceLocator
import com.placer.data.AppPrefs
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.place.LoadPlacesUseCase
import com.placer.domain.usecase.place.LoadUserPlacesUseCase
import com.placer.domain.usecase.user.LoadUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class UserViewViewModel(
    private val loadPlacesUseCase: LoadUserPlacesUseCase = ServiceLocator.instance.placeComponent.loadUserPlacesUseCase,
    private val loadUserUseCase: LoadUserUseCase = ServiceLocator.instance.userComponent.loadUserUseCase,
    private var userId: String = ""
) : BaseViewModel()
{

    constructor(userId: String) : this(){
        this.userId = userId
        reload()
    }

    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<UserView>
    get() = _user.map { it.toView() }

    private var _places: MutableLiveData<List<Place>> = MutableLiveData()
    val places: LiveData<List<PlaceView>>
    get() = _places.map { it.toViews() }

    private var _isClient: MutableLiveData<Boolean> = MutableLiveData()
    val isClient: LiveData<Boolean>
    get() = _isClient

    fun reload() {
        loadPlaces()
        loadUser()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadUserPlaces(userId).first()
            if(result.isSuccess){
                _places.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    private fun loadUser() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = loadUserUseCase.loadUser(userId).first()
            if(result.isSuccess){
                _user.value = result.getOrNull()
                _isClient.value = _user.value?.id == AppPrefs.getUserId()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isRefreshing.value = false
        }
    }

    class Factory(
        private val userId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewViewModel(userId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}