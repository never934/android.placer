package com.placer.domain.repository

import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun loadPlaceById(placeId: String) : Flow<Result<Place>>
    suspend fun loadPlaces() : Flow<Result<List<Place>>>
    suspend fun loadPlacesFromCache() : Flow<Result<List<Place>>>
    suspend fun publishPlace(place: PlaceRequest) : Flow<Result<Place>>
    suspend fun loadUserPlaces(userId: String) : Flow<Result<List<Place>>>
    suspend fun deletePlace(placeId: String) : Flow<Result<Boolean>>
    suspend fun updatePlace(placeId: String, place: PlaceRequest) : Flow<Result<Place>>
}