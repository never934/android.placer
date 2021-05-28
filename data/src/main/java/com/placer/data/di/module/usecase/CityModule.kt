package com.placer.data.di.module.usecase

import com.placer.data.api.CitiesApi
import com.placer.data.repository.CityRepositoryImpl
import com.placer.data.repository.fake.FakeCityRepository
import com.placer.domain.repository.CityRepository
import com.placer.domain.usecase.city.LoadCitiesUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CityModule(
    private val state: ModuleState,
    private val cityRepository: CityRepository? = null
    ) {

    @Provides
    @Singleton
    fun providesLoadCitiesUseCase(cityRepository: CityRepository): LoadCitiesUseCase {
        return LoadCitiesUseCase(cityRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesCityRepository(citiesApi: CitiesApi): CityRepository {
        return when(state){
            ModuleState.TEST -> cityRepository ?: FakeCityRepository()
            ModuleState.RUN -> CityRepositoryImpl(citiesApi, Dispatchers.IO)
        }
    }
}