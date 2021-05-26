package com.placer.data.repository

import android.util.Log
import com.placer.data.api.PlaceApi
import com.placer.data.api.response.toDB
import com.placer.data.api.response.toDBs
import com.placer.data.db.place.PlaceDao
import com.placer.data.db.place.toEntities
import com.placer.data.db.place.toEntity
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlaceRepositoryImpl @Inject internal constructor(
    private val placeApi: PlaceApi,
    private val placeDao: PlaceDao,
    private val dispatcher: CoroutineDispatcher
) : PlaceRepository {
    override suspend fun loadPlaceById(placeId: String): Flow<Result<Place>> = flow {
        try {
            val place = placeApi.getPlaceById(placeId)
            val daoPlace = placeDao.updatePlace(place.toDB())
            emit(Result.success(daoPlace.toEntity()))
        }catch (e: Exception){
            Log.e("exception", e.message ?: "null")
            emit(Result.success(placeDao.getPlace(placeId).toEntity()))
        }
    }

    override suspend fun loadPlaces(): Flow<Result<List<Place>>> = flow {
        try {
            val places = placeApi.getPlaces()
            val daoPlaces =
                placeDao.updatePlaces(places.map { placeResponse ->  placeResponse.toDB() })
            emit(Result.success(daoPlaces.map { it.toEntity() }))
        }catch (e: Exception){
            emit(Result.success(placeDao.getPlaces().map { it.toEntity() }))
        }
    }
        .catch { Result.failure<List<Place>>(Exception("Error while loading places")) }
        .flowOn(dispatcher)

    override suspend fun loadPlacesFromCache(): Flow<Result<List<Place>>> = flow {
        emit(Result.success(placeDao.getPlaces().map { it.toEntity() }))
    }
        .catch { Result.failure<List<Place>>(Exception("Error while loading places")) }
        .flowOn(dispatcher)

    override suspend fun publishPlace(place: PlaceRequest): Flow<Result<Place>> = flow {
        try {
            val placeResponse = placeApi.publishPlace(place)
            val daoPlace = placeDao.updatePlace(placeResponse.toDB())
            emit(Result.success(daoPlace.toEntity()))
        }catch (e: Exception){
            emit(Result.failure<Place>(Exception("Error while publishing place")))
        }
    }
        .flowOn(dispatcher)

    override suspend fun loadUserPlaces(userId: String): Flow<Result<List<Place>>> = flow {
        try {
            val userPlaces = placeApi.getUserPlaces(userId)
            val daoPlaces = placeDao.updateUserPlaces(userId, userPlaces.toDBs())
            emit(Result.success(daoPlaces.toEntities()))
        }catch (e: Exception){
            emit(Result.success(placeDao.getPlaces().filter { it.author.id == userId }.toEntities()))
        }
    }
        .flowOn(dispatcher)

    override suspend fun deletePlace(placeId: String): Flow<Result<Boolean>> = flow {
        try {
            placeApi.deletePlace(placeId)
            placeDao.deletePlace(placeId)
            emit(Result.success(true))
        }catch (e: Exception){
            emit(Result.failure<Boolean>(Exception("Error while deleting place")))
        }
    }
        .flowOn(dispatcher)

    override suspend fun updatePlace(placeId: String, place: PlaceRequest): Flow<Result<Place>> = flow {
        try {
            val placeResponse = placeApi.updatePlace(placeId, place)
            val daoPlace = placeDao.updatePlace(placeResponse.toDB())
            emit(Result.success(daoPlace.toEntity()))
        } catch (e: Exception) {
            emit(Result.failure<Place>(Exception("Error while updating place")))
        }
    }
        .flowOn(dispatcher)

}