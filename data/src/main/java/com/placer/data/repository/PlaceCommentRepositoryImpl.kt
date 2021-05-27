package com.placer.data.repository

import com.placer.data.api.PlaceApi
import com.placer.data.api.request.PlaceCommentRequest
import com.placer.data.api.response.toDB
import com.placer.data.db.place.comment.PlaceCommentDao
import com.placer.data.db.place.comment.toEntities
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.repository.PlaceCommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class PlaceCommentRepositoryImpl @Inject internal constructor(
    private val placeApi: PlaceApi,
    private val commentsDao: PlaceCommentDao,
    private val dispatcher: CoroutineDispatcher
) : PlaceCommentRepository {

    override suspend fun loadPlaceComments(placeId: String): Flow<Result<List<PlaceComment>>> = flow {
        try {
            val comments = placeApi.getPlaceComments(placeId)
            val daoComments = commentsDao.updatePlaceComments(placeId, comments.toDB())
            emit(Result.success(daoComments.toEntities()))
        }catch (e: Exception){
            emit(Result.success(commentsDao.getPlaceComments(placeId).toEntities()))
        }
    }
        .flowOn(dispatcher)

    override suspend fun publishPlaceComment(placeId: String, text: String): Flow<Result<List<PlaceComment>>> = flow {
        try {
            val comments = placeApi.publishPlaceComments(placeId, listOf(PlaceCommentRequest(text)))
            val daoComments = commentsDao.updatePlaceComments(placeId, comments.toDB())
            emit(Result.success(daoComments.toEntities()))
        }catch (e: Exception){
            emit(Result.failure<List<PlaceComment>>(Exception("Error while publishing place comment")))
        }
    }
        .flowOn(dispatcher)
}