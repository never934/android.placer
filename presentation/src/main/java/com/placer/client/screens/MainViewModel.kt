package com.placer.client.screens

import androidx.lifecycle.*
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.UserView
import com.placer.client.entity.toView
import com.placer.client.servicelocator.ServiceLocator
import com.placer.data.AppPrefs
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

internal class MainViewModel(private val loadUserUseCase: LoadUserUseCase = ServiceLocator.instance.userComponent.loadUserUseCase) : BaseViewModel() {
    private var _profile: MutableLiveData<User> = MutableLiveData()
    internal val profile: LiveData<UserView>
    get() = _profile.map { it.toView() }

    private var _exitExecute: MutableLiveData<Boolean> = MutableLiveData()
    internal val exitExecute: LiveData<Boolean>
    get() = _exitExecute

    var firstStart: Boolean = true
    var profileEntity: UserView? = null

    init {
        loadProfile()
    }

    private fun loadProfile(){
        viewModelScope.launch {
            val profileResult = loadUserUseCase.loadProfile().first()
            if (profileResult.isSuccess){
                _profile.value = profileResult.getOrNull()
                profileEntity = profileResult.getOrNull()?.toView()
            }else{
                showSnackBar.value = profileResult.exceptionOrNull()?.message
            }
        }
    }

    fun exit(){
        AppPrefs.saveUserId("")
        AppPrefs.saveServerToken("")
        _exitExecute.value = true
    }

    fun exitDone(){
        _exitExecute.value = false
    }
}