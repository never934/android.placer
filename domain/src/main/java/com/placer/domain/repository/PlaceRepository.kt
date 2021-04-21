package com.placer.domain.repository

import com.placer.domain.entity.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun loadPlaces() : Flow<List<Place>>
    suspend fun publishPlace(place: Place) : Flow<Place>
    suspend fun loadUserPlaces(userId: String) : Flow<List<Place>>
    suspend fun deletePlace(placeId: String) : Flow<Boolean>
    suspend fun updatePlace(place: Place) : Flow<Place>
}