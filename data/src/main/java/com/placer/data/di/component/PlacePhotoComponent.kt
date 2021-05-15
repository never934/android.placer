package com.placer.data.di.component

import com.placer.data.di.module.api.PlaceApiModule
import com.placer.data.di.module.dao.PlaceDaoModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import com.placer.data.di.module.room.RoomModule
import com.placer.data.di.module.usecase.PlacePhotoModule
import com.placer.domain.usecase.place.photo.DeletePlacePhotosUseCase
import com.placer.domain.usecase.place.photo.UploadPlacePhotosUseCase
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
        PlacePhotoModule::class
    ]
)
interface PlacePhotoComponent {
    val deletePlacePhotoUseCase: DeletePlacePhotosUseCase
    val uploadPlacePhotoUseCase: UploadPlacePhotosUseCase
}