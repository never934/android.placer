package com.placer.domain.usecase.city

import com.placer.domain.entity.city.City
import com.placer.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoadCitiesUseCase(
    private val cityRepository: CityRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadCities() : Flow<Result<List<City>>> =
        cityRepository.loadCities().flowOn(dispatcher)
}