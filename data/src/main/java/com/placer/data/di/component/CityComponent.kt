package com.placer.data.di.component

import com.placer.data.di.module.api.CitiesApiModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import com.placer.data.di.module.usecase.CityModule
import com.placer.domain.usecase.city.LoadCitiesUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitSettingsModule::class,
        ServerRetrofitModule::class,
        CitiesApiModule::class,
        CityModule::class
    ]
)
interface CityComponent {
    val loadCitiesUseCase: LoadCitiesUseCase
}