package com.placer.domain.repository

import com.placer.domain.entity.city.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun loadCities() : Flow<Result<List<City>>>
}