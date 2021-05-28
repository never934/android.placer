package com.placer.client.screens.places.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.UserView
import com.placer.client.entity.toView
import com.placer.client.servicelocator.ServiceLocator
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class PlacePublishSuccessViewModel(
    private val loadUserUseCase: LoadUserUseCase = ServiceLocator.instance.userComponent.loadUserUseCase
) : BaseViewModel() {

    private var _profile: MutableLiveData<User> = MutableLiveData()
    val profile: LiveData<UserView>
    get() = _profile.map { it.toView() }

    init {
        loadProfile()
    }

    private fun loadProfile() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = loadUserUseCase.loadProfile().first()
            if(result.isSuccess){
                _profile.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isRefreshing.value = false
        }
    }
}