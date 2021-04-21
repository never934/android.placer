package com.placer.domain.repository

import com.placer.domain.entity.Place
import kotlinx.coroutines.flow.Flow

interface PlacePhotoRepository {
    suspend fun deletePlacePhotos(placeId: String, photoIds: List<String>) : Flow<Place>
    suspend fun uploadPlacePhotos(placeId: String, photos: List<ByteArray>) : Flow<Place>
}