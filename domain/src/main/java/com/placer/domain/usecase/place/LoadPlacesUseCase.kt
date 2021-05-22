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

    suspend fun loadPlaceById(placeId: String) : Flow<Result<Place>> =
        placeRepository.loadPlaceById(placeId).flowOn(dispatcher)

    suspend fun loadPlacesByInputFromCacheWithEmptyFilter(input: String) : Flow<Result<List<Place>>> =
        placeRepository.loadPlacesFromCache()
            .map {
                it.getOrNull()?.let { list ->
                    Result.success(list.filter { place ->
                        place.name.toUpperCase(Locale.ROOT)
                            .contains(input.toUpperCase(Locale.ROOT)) && input.isNotEmpty()
                    })
                } ?: run {
                    it
                }
            }
            .flowOn(dispatcher)

    suspend fun loadPlacesByInputFromCache(input: String) : Flow<Result<List<Place>>> =
        placeRepository.loadPlacesFromCache()
            .map {
                it.getOrNull()?.let { list ->
                    Result.success(list.filter { place ->
                        place.name.toUpperCase(Locale.ROOT)
                            .contains(input.toUpperCase(Locale.ROOT)) || place.name.isEmpty()
                    })
                } ?: run {
                    it
                }
            }
            .flowOn(dispatcher)

    suspend fun loadPlacesByInputForTop(input: String) : Flow<Result<List<Place>>> =
        placeRepository.loadPlacesFromCache()
            .map {
                it.getOrNull()?.let { list ->
                    Result.success(list.filter { place ->
                        place.name.toUpperCase(Locale.ROOT)
                            .contains(input.toUpperCase(Locale.ROOT)) || place.name.isEmpty()
                    }.sortedBy { place -> place.topPosition }.reversed())
                } ?: run {
                    it
                }
            }
            .flowOn(dispatcher)
}