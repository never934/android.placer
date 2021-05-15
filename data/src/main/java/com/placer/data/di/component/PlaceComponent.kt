package com.placer.data.di.component

import com.placer.data.di.module.api.PlaceApiModule
import com.placer.data.di.module.dao.PlaceDaoModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import com.placer.data.di.module.room.RoomModule
import com.placer.data.di.module.usecase.PlaceModule
import com.placer.domain.usecase.place.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitSettingsModule::class,
        ServerRetrofitModule::class,
        RoomModule::class,
        PlaceApiModule::class,
        PlaceDaoModule::class,
        PlaceModule::class
    ]
)
interface PlaceComponent {
    val deletePlaceUseCase: DeletePlaceUseCase
    val loadPlacesUseCase: LoadPlacesUseCase
    val loadUserPlacesUseCase: LoadUserPlacesUseCase
    val publishPlacesUseCase: PublishPlaceUseCase
    val updatePlaceUseCase: UpdatePlaceUseCase
}