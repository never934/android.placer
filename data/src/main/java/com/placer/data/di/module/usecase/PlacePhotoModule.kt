package com.placer.data.di.module.usecase

import com.placer.data.api.PlaceApi
import com.placer.data.db.place.PlaceDao
import com.placer.data.repository.PlacePhotoRepositoryImpl
import com.placer.domain.repository.PlacePhotoRepository
import com.placer.domain.usecase.place.photo.DeletePlacePhotosUseCase
import com.placer.domain.usecase.place.photo.UploadPlacePhotosUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class PlacePhotoModule {

    @Provides
    @Singleton
    fun providesDeletePlacePhotosUseCase(
        placePhotoRepository: PlacePhotoRepository
    ): DeletePlacePhotosUseCase {
        return DeletePlacePhotosUseCase(placePhotoRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesUploadPlacePhotosUseCase(
        placePhotoRepository: PlacePhotoRepository
    ): UploadPlacePhotosUseCase {
        return UploadPlacePhotosUseCase(placePhotoRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesPlacePhotoRepository(
        placeApi: PlaceApi, placeDao: PlaceDao
    ): PlacePhotoRepository {
        return PlacePhotoRepositoryImpl(placeApi, placeDao, Dispatchers.IO)
    }
}