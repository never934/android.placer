package com.placer.data.di.component

import com.placer.data.di.module.api.UserApiModule
import com.placer.data.di.module.dao.UserDaoModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import com.placer.data.di.module.room.RoomModule
import com.placer.data.di.module.usecase.UserModule
import com.placer.domain.usecase.user.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitSettingsModule::class,
        ServerRetrofitModule::class,
        RoomModule::class,
        UserApiModule::class,
        UserDaoModule::class,
        UserModule::class
    ]
)
interface UserComponent {
    val loadUsersUseCase: LoadUsersUseCase
    val loadUserUseCase: LoadUserUseCase
    val updateUserAvatarUseCase: UpdateUserAvatarUseCase
    val updateUserCityUseCase: UpdateUserCityUseCase
    val updateUserUseCase: UpdateUserUseCase
}