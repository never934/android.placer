package com.placer.client.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placer.client.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val isRefreshing: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
}