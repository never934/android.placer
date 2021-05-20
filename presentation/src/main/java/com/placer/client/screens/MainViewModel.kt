package com.placer.client.screens

import android.util.Log
import androidx.lifecycle.*
import com.placer.client.base.BaseViewModel
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.place.LoadPlacesUseCase
import com.placer.domain.usecase.user.LoadUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val loadUserUseCase: LoadUserUseCase) : BaseViewModel() {
    private var _profile: MutableLiveData<User> = MutableLiveData()
    val profile: LiveData<User>
    get() = _profile

    init {
        loadProfile()
    }

    private fun loadProfile(){
        viewModelScope.launch {
            val profileResult = loadUserUseCase.loadProfile().first()
            if (profileResult.isSuccess){
                Log.e("profile", profileResult.getOrNull()?.name ?: "null")
                _profile.value = profileResult.getOrNull()
            }else{
                showSnackBar.value = profileResult.exceptionOrNull()?.message
            }
        }
    }

    class Factory(private val loadUserUseCase: LoadUserUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(loadUserUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}