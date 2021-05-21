package com.placer.domain.repository

import com.placer.domain.entity.place.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun loadPlaces() : Flow<Result<List<Place>>>
    suspend fun loadPlacesFromCache() : Flow<Result<List<Place>>>
    suspend fun publishPlace(place: Place) : Flow<Result<Place>>
    suspend fun loadUserPlaces(userId: String) : Flow<Result<List<Place>>>
    suspend fun deletePlace(placeId: String) : Flow<Result<Boolean>>
    suspend fun updatePlace(place: Place) : Flow<Result<Place>>
}