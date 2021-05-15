package com.placer.data.di.component

import com.placer.data.di.module.api.PlaceApiModule
import com.placer.data.di.module.dao.PlaceCommentDaoModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import com.placer.data.di.module.room.RoomModule
import com.placer.data.di.module.usecase.PlaceCommentModule
import com.placer.domain.usecase.place.comment.LoadPlaceCommentsUseCase
import com.placer.domain.usecase.place.comment.PublishPlaceCommentUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitSettingsModule::class,
        ServerRetrofitModule::class,
        RoomModule::class,
        PlaceApiModule::class,
        PlaceCommentDaoModule::class,
        PlaceCommentModule::class
    ]
)
interface PlaceCommentComponent {
    val publishPlaceCommentUseCase: PublishPlaceCommentUseCase
    val loadPlaceCommentUseCase: LoadPlaceCommentsUseCase
}