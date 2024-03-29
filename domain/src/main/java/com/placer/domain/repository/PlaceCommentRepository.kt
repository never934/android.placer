package com.placer.domain.repository

import com.placer.domain.entity.place.PlaceComment
import kotlinx.coroutines.flow.Flow

interface PlaceCommentRepository {
    suspend fun loadPlaceComments(placeId: String) : Flow<Result<List<PlaceComment>>>
    suspend fun publishPlaceComment(placeId: String, text: String) : Flow<Result<List<PlaceComment>>>
}