package com.placer.domain.fake

import com.placer.domain.TestUtils
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import kotlin.collections.ArrayList

internal class FakePlaceRepository(private val places: ArrayList<Place> = arrayListOf()) : PlaceRepository {

    var error = false

    override suspend fun loadPlaces(): Flow<Result<List<Place>>> = flow {
        emit(Result.success(places))
    }

    override suspend fun loadPlacesFromCache(): Flow<Result<List<Place>>> = flow {
        emit(Result.success(places))
    }

    override suspend fun publishPlace(place: PlaceRequest): Flow<Result<Place>> = flow {
        if (error.not()){
            val placeEntity = Place(
                id = UUID.randomUUID().toString(),
                name = place.name,
                description = place.description,
                cityName = TestUtils.randomString(),
                lat = place.lat,
                lng = place.lng,
                published = true,
                author = TestUtils.getRandomUser(),
                commentsCount = 0,
                topPosition = (places.size+1).toLong(),
                photos = listOf(),
                createdDate = System.currentTimeMillis()
            )
            places.add(placeEntity)
            emit(Result.success(placeEntity))
        }else{
            emit(Result.failure<Place>(Exception("Error while publishing place")))
        }
    }

    override suspend fun loadUserPlaces(userId: String): Flow<Result<List<Place>>> = flow {
        emit(Result.success(places.filter { it.author.id == userId }))
    }

    override suspend fun deletePlace(placeId: String): Flow<Result<Boolean>> = flow {
        if(error.not()){
            places.remove(places.first { it.id == placeId })
            emit(Result.success(true))
        }else{
            emit(Result.failure<Boolean>(Exception("Error while deleting place")))
        }
    }

    override suspend fun loadPlaceById(placeId: String): Flow<Result<Place>> = flow {
        emit(Result.success(places.first { it.id == placeId }))
    }

    override suspend fun updatePlace(placeId: String, place: PlaceRequest): Flow<Result<Place>> = flow {
        val placeForUpdate = places.first { it.id == placeId }
        places.removeIf { it.id == placeId }
        places.add(
            Place(
                id = placeForUpdate.id,
                name = place.name,
                description = place.description,
                lat = place.lat,
                lng = place.lng,
                published = place.published,
                author = placeForUpdate.author,
                cityName = placeForUpdate.cityName,
                photos = placeForUpdate.photos,
                commentsCount = placeForUpdate.commentsCount,
                topPosition = placeForUpdate.topPosition,
                createdDate = placeForUpdate.createdDate
            )
        )
        emit(Result.success(places.first { it.id == placeId }))
    }
}