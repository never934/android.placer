package com.placer.data.repository

import com.placer.data.api.PlaceApi
import com.placer.data.api.request.toRequest
import com.placer.data.api.response.toDB
import com.placer.data.db.place.PlaceDao
import com.placer.data.db.place.toEntity
import com.placer.domain.entity.place.Place
import com.placer.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlaceRepositoryImpl @Inject internal constructor(
    private val placeApi: PlaceApi,
    private val placeDao: PlaceDao,
    private val dispatcher: CoroutineDispatcher
) : PlaceRepository {
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

    override suspend fun publishPlace(place: Place): Flow<Result<Place>> =
        placeApi.publishPlace(place.toRequest())
            .map {
                placeDao.savePlace(it.toDB())
                Result.success(placeDao.getPlace(it.id).toEntity())
            }
            .catch { Result.failure<Place>(Exception("Error while publishing place")) }
            .flowOn(dispatcher)

    override suspend fun loadUserPlaces(userId: String): Flow<Result<List<Place>>> =
        placeApi.getUserPlaces(userId)
            .map {
                placeDao.updateUserPlaces(userId, it.map { placeResponse -> placeResponse.toDB() })
            }
            .map { Result.success(it.map { placeDB -> placeDB.toEntity() }) }
            .catch { placeDao.getPlaces().map { it.toEntity() } }
            .flowOn(dispatcher)

    override suspend fun deletePlace(placeId: String): Flow<Result<Boolean>> =
        placeApi.deletePlace(placeId)
            .map { Result.success(true) }
            .catch { Result.failure<Boolean>(Exception("Error while deleting place")) }
            .flowOn(dispatcher)

    override suspend fun updatePlace(place: Place): Flow<Result<Place>> =
        placeApi.updatePlace(place.id, place.toRequest())
            .map { placeDao.updatePlace(it.toDB()) }
            .map { Result.success(placeDao.getPlace(place.id).toEntity()) }
            .catch { Result.failure<Place>(Exception("Error while updating place")) }
            .flowOn(dispatcher)

}