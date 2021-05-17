package com.placer.client.screens

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.placer.client.base.BaseViewModel
import com.placer.client.util.FirebaseUserLiveData
import com.placer.data.AppPrefs
import com.placer.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel(private val signInUseCase: SignInUseCase) : BaseViewModel() {

    private var _loginSuccessed: MutableLiveData<Boolean> = MutableLiveData(false)
    val loginSuccessed: LiveData<Boolean>
    get() = _loginSuccessed

   fun signIn(idToken: String){
        viewModelScope.launch {
            val result = signInUseCase.signIn(idToken).first()
            if (result.isSuccess){
                result.getOrNull()?.let {
                    AppPrefs.saveServerToken(it)
                    _loginSuccessed.value = true
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