package com.placer.data.di.component

import com.placer.data.api.AuthApi
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.api.AuthApiModule
import com.placer.data.di.module.retrofit.AuthRetrofitModule
import com.placer.data.di.module.usecase.AuthModule
import com.placer.domain.usecase.auth.SignInUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitSettingsModule::class,
        AuthRetrofitModule::class,
        AuthApiModule::class,
        AuthModule::class
    ]
)
interface AuthComponent {
    val signInUseCase: SignInUseCase
}