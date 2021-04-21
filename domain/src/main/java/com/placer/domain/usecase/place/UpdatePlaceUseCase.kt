package com.placer.domain.usecase.place

import com.placer.domain.entity.Place
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UpdatePlaceUseCase(
    private val placeRepository: PlaceRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun updatePlace(place: Place) : Flow<Place> =
        placeRepository.updatePlace(place).flowOn(dispatcher)
}