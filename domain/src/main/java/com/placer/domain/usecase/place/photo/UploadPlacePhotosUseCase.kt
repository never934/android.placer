package com.placer.domain.usecase.place.photo

import com.placer.domain.entity.place.Place
import com.placer.domain.repository.PlacePhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UploadPlacePhotosUseCase(
    private val placePhotoRepository: PlacePhotoRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun uploadPlacePhotos(placeId: String, photos: List<ByteArray>) : Flow<Result<Place>> =
        placePhotoRepository.uploadPlacePhotos(placeId, photos).flowOn(dispatcher)
}