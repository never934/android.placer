package com.placer.domain.usecase.place

import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DeletePlaceUseCase(
    private val placeRepository: PlaceRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun deletePlace(placeId: String) : Flow<Result<Boolean>> =
        placeRepository.deletePlace(placeId).flowOn(dispatcher)
}