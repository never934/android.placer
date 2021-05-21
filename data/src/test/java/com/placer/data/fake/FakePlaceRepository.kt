package com.placer.data.fake

import com.placer.domain.entity.place.Place
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePlaceRepository : PlaceRepository {

    var error = false
    private val places: ArrayList<Place> = arrayListOf()

    override suspend fun loadPlaces(): Flow<Result<List<Place>>> = flow {
        emit(Result.success(places))
    }

    override suspend fun loadPlacesFromCache(): Flow<Result<List<Place>>> = flow {
        emit(Result.success(places))
    }

    override suspend fun publishPlace(place: Place): Flow<Result<Place>>  = flow {
        if (error.not()){
            places.add(place)
            emit(Result.success(place))
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

    override suspend fun updatePlace(place: Place): Flow<Result<Place>> = flow {
        // server logic (data val from backend)
    }
}