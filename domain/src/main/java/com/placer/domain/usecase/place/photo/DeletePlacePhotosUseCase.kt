package com.placer.domain.usecase.place.photo

import com.placer.domain.entity.Place
import com.placer.domain.repository.PlacePhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DeletePlacePhotosUseCase(
    private val placePhotoRepository: PlacePhotoRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun deletePlacePhotos(placeId: String, photoIds: List<String>) : Flow<Result<Place>> =
        placePhotoRepository.deletePlacePhotos(placeId, photoIds).flowOn(dispatcher)
}