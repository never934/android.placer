package com.placer.data.repository

import com.placer.data.api.PlaceApi
import com.placer.data.api.response.toDB
import com.placer.data.db.place.PlaceDao
import com.placer.data.db.place.toEntity
import com.placer.data.utils.Extensions.toMultipartPhoto
import com.placer.domain.entity.place.Place
import com.placer.domain.repository.PlacePhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlacePhotoRepositoryImpl @Inject internal constructor(
    private val placeApi: PlaceApi,
    private val placeDao: PlaceDao,
    private val dispatcher: CoroutineDispatcher
) : PlacePhotoRepository {

    override suspend fun deletePlacePhotos(
        placeId: String,
        photoIds: List<String>
    ): Flow<Result<Place>> =
        placeApi.deletePlacePhotos(placeId, photoIds)
            .map { placeDao.savePlace(it.toDB()) }
            .map { Result.success(placeDao.getPlace(placeId).toEntity()) }
            .catch { Result.failure<Place>(Exception("Error while photos deleting")) }
            .flowOn(dispatcher)

    override suspend fun uploadPlacePhoto(
        placeId: String,
        photo: ByteArray
    ): Flow<Result<Place>> =
        placeApi.publishPlacePhoto(placeId, photo.toMultipartPhoto("file"))
            .map { placeDao.savePlace(it.toDB()) }
            .map { Result.success(placeDao.getPlace(placeId).toEntity()) }
            .catch { Result.failure<Place>(Exception("Error while photo uploading")) }
            .flowOn(dispatcher)

}