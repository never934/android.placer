package com.placer.data.di.module.usecase

import com.placer.data.api.PlaceApi
import com.placer.data.db.place.PlaceDao
import com.placer.data.repository.PlaceRepositoryImpl
import com.placer.data.repository.fake.FakePlaceRepository
import com.placer.domain.repository.PlaceRepository
import com.placer.domain.usecase.place.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class PlaceModule(
    private val state: ModuleState,
    private val placeRepository: PlaceRepository? = null
    ) {

    @Provides
    @Singleton
    fun providesDeletePlaceUseCase(placeRepository: PlaceRepository): DeletePlaceUseCase {
        return DeletePlaceUseCase(placeRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesLoadPlacesUseCase(placeRepository: PlaceRepository): LoadPlacesUseCase {
        return LoadPlacesUseCase(placeRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesLoadUserPlacesUseCase(placeRepository: PlaceRepository): LoadUserPlacesUseCase {
        return LoadUserPlacesUseCase(placeRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesPublishPlaceUseCase(placeRepository: PlaceRepository): PublishPlaceUseCase {
        return PublishPlaceUseCase(placeRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesUpdatePlaceUseCase(placeRepository: PlaceRepository): UpdatePlaceUseCase {
        return UpdatePlaceUseCase(placeRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesPlaceRepository(placeApi: PlaceApi, placeDao: PlaceDao): PlaceRepository {
        return when(state){
            ModuleState.TEST -> placeRepository ?: FakePlaceRepository()
            ModuleState.RUN -> PlaceRepositoryImpl(placeApi, placeDao, Dispatchers.IO)
        }
    }
}