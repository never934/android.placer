package com.placer.client.screens.auth

import androidx.lifecycle.*
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.servicelocator.ServiceLocator
import com.placer.data.AppPrefs
import com.placer.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(private val signInUseCase: SignInUseCase = ServiceLocator.instance.authComponent.signInUseCase) : BaseViewModel() {

    private var _loginSuccessed: MutableLiveData<Boolean> = MutableLiveData()
    val loginSuccessed: LiveData<Boolean>
    get() = _loginSuccessed

    private var _userId: MutableLiveData<String> = MutableLiveData()
    val userId: LiveData<String>
    get() = _userId

   fun signIn(idToken: String){
        viewModelScope.launch {
            val result = signInUseCase.signIn(idToken).first()
            if (result.isSuccess){
                result.getOrNull()?.let {
                    _loginSuccessed.value = true
                    _userId.value = it
                }
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }
}