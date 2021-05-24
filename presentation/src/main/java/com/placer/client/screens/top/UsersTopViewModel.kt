package com.placer.client.screens.top

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.UserView
import com.placer.client.entity.toViews
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class UsersTopViewModel(
    private val loadUsersUseCase: LoadUsersUseCase = AppClass.appInstance.userComponent.loadUsersUseCase
) : BaseViewModel() {

    private var _topUsers: MutableLiveData<List<User>> = MutableLiveData()
    val topUsers: LiveData<List<UserView>>
        get() = _topUsers.map { it.toViews() }

    init {
        reload()
    }

    fun reload() {
        isRefreshing.value = true
        viewModelScope.launch {
            loadUsers()
            isRefreshing.value = false
        }
    }

    private suspend fun loadUsers() {
        val result = loadUsersUseCase.loadUsersForTop().first()
        if(result.isSuccess){
            _topUsers.value = result.getOrNull()
        }else{
            showSnackBar.value = result.exceptionOrNull()?.message
        }
    }

    fun loadUsers(input: String) {
        viewModelScope.launch {
            val result = loadUsersUseCase.loadUsersByInputForTop(input).first()
            if (result.isSuccess){
                _topUsers.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun userClicked(user: UserView) {
        Log.e("clicked", "user ${user.name}")
    }

}