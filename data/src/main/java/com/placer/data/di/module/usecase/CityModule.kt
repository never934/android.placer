package com.placer.data.di.module.usecase

import com.placer.data.api.CitiesApi
import com.placer.data.repository.CityRepositoryImpl
import com.placer.domain.repository.CityRepository
import com.placer.domain.usecase.city.LoadCitiesUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CityModule {

    @Provides
    @Singleton
    fun providesLoadCitiesUseCase(cityRepository: CityRepository): LoadCitiesUseCase {
        return LoadCitiesUseCase(cityRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesCityRepository(citiesApi: CitiesApi): CityRepository {
        return CityRepositoryImpl(citiesApi, Dispatchers.IO)
    }
}