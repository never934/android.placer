package com.placer.domain.usecase.place

import com.placer.domain.entity.place.Place
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import java.util.*

class LoadPlacesUseCase(
    private val placeRepository: PlaceRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadPlaces() : Flow<Result<List<Place>>> =
        placeRepository.loadPlaces().flowOn(dispatcher)

    suspend fun loadPlacesBySearch(input: String) : Flow<Result<List<Place>>> =
        placeRepository.loadPlaces()
            .map {
                Result.success(it.getOrNull()!!.filter { place ->
                    place.name.toUpperCase(Locale.ROOT)
                        .contains(input.toUpperCase(Locale.ROOT)) && input.isNotEmpty()
                })
            }
            .catch { Result.failure<List<Place>>(Exception()) }
            .flowOn(dispatcher)
}