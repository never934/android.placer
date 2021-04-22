package com.placer.data.di.component

import com.placer.data.api.CitiesApi
import com.placer.data.api.PlaceApi
import com.placer.data.api.UserApi
import com.placer.data.di.module.api.CitiesApiModule
import com.placer.data.di.module.api.PlaceApiModule
import com.placer.data.di.module.api.UserApiModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitSettingsModule::class,
        ServerRetrofitModule::class,
        CitiesApiModule::class,
        PlaceApiModule::class,
        UserApiModule::class
    ]
)
interface ServerComponent {
    val citiesApi: CitiesApi
    val placeApi: PlaceApi
    val userApi: UserApi
}