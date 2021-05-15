package com.placer.client.base

import android.app.Application
import androidx.lifecycle.ViewModel
import com.placer.client.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
}