package com.placer.domain.usecase.place.comment

import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.repository.PlaceCommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoadPlaceCommentsUseCase(
    private val placeCommentRepository: PlaceCommentRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadPlaceComments(placeId: String) : Flow<Result<List<PlaceComment>>> =
        placeCommentRepository.loadPlaceComments(placeId).flowOn(dispatcher)
}