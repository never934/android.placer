package com.placer.domain.repository

import com.placer.domain.entity.place.Place
import kotlinx.coroutines.flow.Flow

interface PlacePhotoRepository {
    suspend fun deletePlacePhotos(placeId: String, photoIds: List<String>) : Flow<Result<Place>>
    suspend fun uploadPlacePhoto(placeId: String, photo: ByteArray) : Flow<Result<Place>>
}