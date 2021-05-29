package com.placer.data.repository.fake

import com.placer.data.utils.EspressoIdlingResource.wrapEspressoIdlingResource
import com.placer.domain.entity.city.City
import com.placer.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCityRepository : CityRepository {

    var error = false

    override suspend fun loadCities(input: String): Flow<Result<List<City>>> = wrapEspressoIdlingResource {
        flow {
            if (error.not()){
                emit(Result.success(listOf<City>()))
            }else{
                emit(Result.failure<List<City>>(Exception("Error while cities loading")))
            }
        }
    }
}