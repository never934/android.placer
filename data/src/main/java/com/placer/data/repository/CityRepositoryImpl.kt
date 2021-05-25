package com.placer.data.repository

import com.placer.data.api.CitiesApi
import com.placer.data.api.response.toEntities
import com.placer.domain.entity.city.City
import com.placer.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CityRepositoryImpl @Inject internal constructor(
    private val citiesApi: CitiesApi,
    private val dispatcher: CoroutineDispatcher
) : CityRepository {

    override suspend fun loadCities(input: String): Flow<Result<List<City>>> = flow {
        try {
            val cities = citiesApi.getCities(input)
            emit(Result.success(cities.toEntities()))
        }catch(e: Exception){
            emit(Result.failure<List<City>>(Exception("Error while loading cities")))
        }
    }
        .flowOn(dispatcher)

}