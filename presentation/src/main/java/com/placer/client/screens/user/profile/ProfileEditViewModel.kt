package com.placer.client.screens.user.profile

import androidx.lifecycle.*
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.UserView
import com.placer.client.entity.toView
import com.placer.data.AppPrefs
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUserUseCase
import com.placer.domain.usecase.user.UpdateUserAvatarUseCase
import com.placer.domain.usecase.user.UpdateUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.InputStream

internal class ProfileEditViewModel(
    private val loadUserUseCase: LoadUserUseCase = AppClass.appInstance.userComponent.loadUserUseCase,
    private val updateProfileUseCase: UpdateUserUseCase = AppClass.appInstance.userComponent.updateUserUseCase,
    private val updateAvatarUseCase: UpdateUserAvatarUseCase = AppClass.appInstance.userComponent.updateUserAvatarUseCase
) : BaseViewModel()
{
    init {
        loadProfile()
    }

    private var _profile: MutableLiveData<User> = MutableLiveData()
    val profile: LiveData<UserView>
    get() = _profile.map { it.toView() }

    fun loadProfile() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = loadUserUseCase.loadProfile().first()
            if(result.isSuccess){
                _profile.postValue(result.getOrNull())
            }else{
                showSnackBar.postValue(result.exceptionOrNull()?.message)
            }
            isRefreshing.value = false
        }
    }

    fun updateProfile(userName: String, userNickname: String) {
        if (userName.isEmpty() && userNickname.isEmpty()){
            showSnackBar.value = AppClass.appInstance.getString(R.string.common_err_name_nickname_empty)
        }else{
            isLoading.value = true
            viewModelScope.launch {
                val result = updateProfileUseCase.updateUser(userName, userNickname).first()
                if(result.isSuccess){
                    _profile.value = result.getOrNull()
                }else{
                    showSnackBar.value = result.exceptionOrNull()?.message
                }
                isLoading.value = false
            }
        }
    }

    fun updateAvatar(avatar: InputStream) {
        isLoading.value = true
        viewModelScope.launch {
            val result = updateAvatarUseCase.updateUserAvatar(AppPrefs.getUserId(), avatar.readBytes()).first()
            if(result.isSuccess){
                _profile.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }
}