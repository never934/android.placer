package com.placer.domain.repository

import com.placer.domain.entity.PlaceComment
import kotlinx.coroutines.flow.Flow

interface PlaceCommentRepository {
    suspend fun loadPlaceComments(placeId: String) : Flow<List<PlaceComment>>
    suspend fun publishPlaceComment(comment: PlaceComment) : Flow<List<PlaceComment>>
}