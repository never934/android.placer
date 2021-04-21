package com.placer.domain.repository

import com.placer.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun loadCities() : Flow<List<City>>
}