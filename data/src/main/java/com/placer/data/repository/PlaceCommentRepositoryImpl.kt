package com.placer.data.repository

import com.placer.data.api.PlaceApi
import com.placer.data.api.request.PlaceCommentRequest
import com.placer.data.api.response.toDB
import com.placer.data.db.place.comment.PlaceCommentDao
import com.placer.data.db.place.comment.toEntities
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.repository.PlaceCommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class PlaceCommentRepositoryImpl @Inject internal constructor(
    private val placeApi: PlaceApi,
    private val commentsDao: PlaceCommentDao,
    private val dispatcher: CoroutineDispatcher
) : PlaceCommentRepository {
    override suspend fun loadPlaceComments(placeId: String): Flow<Result<List<PlaceComment>>> =
        placeApi.getPlaceComments(placeId)
            .map { commentsDao.updatePlaceComments(placeId, it.toDB()) }
            .map { Result.success(commentsDao.getPlaceComments(placeId).toEntities()) }
            .catch { Result.success(commentsDao.getPlaceComments(placeId).toEntities()) }
            .flowOn(dispatcher)

    override suspend fun publishPlaceComment(placeId: String, text: String): Flow<Result<List<PlaceComment>>> =
        placeApi.publishPlaceComments(placeId, listOf(PlaceCommentRequest(text)))
            .map { commentsDao.updatePlaceComments(placeId, it.toDB()) }
            .map { Result.success(commentsDao.getPlaceComments(placeId).toEntities()) }
            .catch { Result.failure<List<PlaceComment>>(Exception("Error while publishing place comment")) }
            .flowOn(dispatcher)

}