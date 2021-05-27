package com.placer.client.fake

import com.placer.domain.entity.city.City
import com.placer.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FakeCityRepository : CityRepository {

    var error = false

    override suspend fun loadCities(input: String): Flow<Result<List<City>>> = flow {
        if (error.not()){
            emit(Result.success(listOf<City>()))
        }else{
            emit(Result.failure<List<City>>(Exception("Error while cities loading")))
        }
    }
}