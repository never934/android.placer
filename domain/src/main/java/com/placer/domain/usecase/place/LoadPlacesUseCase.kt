package com.placer.domain.usecase.place

import com.placer.domain.entity.Place
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoadPlacesUseCase(
    private val placeRepository: PlaceRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadPlaces() : Flow<Result<List<Place>>> =
        placeRepository.loadPlaces().flowOn(dispatcher)
}