package com.placer.domain.usecase.place.comment

import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.repository.PlaceCommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PublishPlaceCommentUseCase(
    private val placeCommentRepository: PlaceCommentRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun publishPlaceComment(placeId: String, text: String) : Flow<Result<List<PlaceComment>>> =
        placeCommentRepository.publishPlaceComment(placeId, text).flowOn(dispatcher)
}