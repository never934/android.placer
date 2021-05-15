package com.placer.client.screens

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.placer.client.base.BaseViewModel
import com.placer.client.util.FirebaseUserLiveData
import com.placer.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel(private val signInUseCase: SignInUseCase) : BaseViewModel() {

   fun signIn(idToken: String){
        Log.e("viewModel","sign in request")
        viewModelScope.launch {
            val result = signInUseCase.signIn(idToken).first()
            if (result.isSuccess){
                showSnackBar.value = "success login"
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