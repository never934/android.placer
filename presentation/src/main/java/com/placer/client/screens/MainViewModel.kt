package com.placer.client.screens

import androidx.lifecycle.*
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.UserView
import com.placer.client.entity.toView
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class MainViewModel(private val loadUserUseCase: LoadUserUseCase) : BaseViewModel() {
    private var _profile: MutableLiveData<User> = MutableLiveData()
    internal val profile: LiveData<UserView>
    get() = _profile.map { it.toView() }

    init {
        loadProfile()
    }

    private fun loadProfile(){
        viewModelScope.launch {
            val profileResult = loadUserUseCase.loadProfile().first()
            if (profileResult.isSuccess){
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