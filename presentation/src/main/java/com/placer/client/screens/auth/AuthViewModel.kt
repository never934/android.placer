package com.placer.client.screens.auth

import androidx.lifecycle.*
import com.placer.client.base.BaseViewModel
import com.placer.data.AppPrefs
import com.placer.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(private val signInUseCase: SignInUseCase) : BaseViewModel() {

    private var _loginSuccessed: MutableLiveData<Boolean> = MutableLiveData(false)
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

    class Factory(private val signInUseCase: SignInUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(signInUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}