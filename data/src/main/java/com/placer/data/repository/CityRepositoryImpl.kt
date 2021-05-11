package com.placer.data.repository

import com.placer.data.api.CitiesApi
import com.placer.data.api.response.toEntities
import com.placer.domain.entity.city.City
import com.placer.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class CityRepositoryImpl @Inject internal constructor(
    private val citiesApi: CitiesApi,
    private val dispatcher: CoroutineDispatcher
) : CityRepository {
    override suspend fun loadCities(input: String): Flow<Result<List<City>>> =
        citiesApi.getCities(input)
            .map { Result.success(it.toEntities()) }
            .catch { Result.failure<List<City>>(Exception("Error while loading cities")) }
            .flowOn(dispatcher)
}