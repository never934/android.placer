package com.placer.domain.usecase.place

import com.placer.domain.entity.Place
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoadUserPlacesUseCase(
    private val placeRepository: PlaceRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadUserPlaces(userId: String) : Flow<Result<List<Place>>> =
        placeRepository.loadUserPlaces(userId).flowOn(dispatcher)
}