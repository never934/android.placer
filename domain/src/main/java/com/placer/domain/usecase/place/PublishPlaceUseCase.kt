package com.placer.domain.usecase.place

import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PublishPlaceUseCase(
    private val placeRepository: PlaceRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun publishPlace(place: PlaceRequest) : Flow<Result<Place>> =
        placeRepository.publishPlace(place).flowOn(dispatcher)
}