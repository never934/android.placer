package com.placer.data.di.module.usecase

import com.placer.data.api.PlaceApi
import com.placer.data.db.place.comment.PlaceCommentDao
import com.placer.data.repository.PlaceCommentRepositoryImpl
import com.placer.domain.repository.PlaceCommentRepository
import com.placer.domain.usecase.place.comment.LoadPlaceCommentsUseCase
import com.placer.domain.usecase.place.comment.PublishPlaceCommentUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class PlaceCommentModule {

    @Provides
    @Singleton
    fun providesLoadPlaceCommentsUseCase(
        placeCommentRepository: PlaceCommentRepository
    ): LoadPlaceCommentsUseCase {
        return LoadPlaceCommentsUseCase(placeCommentRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesPublishPlaceCommentUseCase(
        placeCommentRepository: PlaceCommentRepository
    ): PublishPlaceCommentUseCase {
        return PublishPlaceCommentUseCase(placeCommentRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesPlaceRepository(
        placeApi: PlaceApi, placeCommentDao: PlaceCommentDao
    ): PlaceCommentRepository {
        return PlaceCommentRepositoryImpl(placeApi, placeCommentDao, Dispatchers.IO)
    }
}