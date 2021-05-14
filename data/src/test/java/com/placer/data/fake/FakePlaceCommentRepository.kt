package com.placer.data.fake

import com.placer.data.TestUtils
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.repository.PlaceCommentRepository
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.collections.ArrayList

class FakePlaceCommentRepository() : PlaceCommentRepository {

    var error = false
    private val comments: ArrayList<PlaceComment> = arrayListOf()

    override suspend fun loadPlaceComments(placeId: String): Flow<Result<List<PlaceComment>>> = flow {
        emit(Result.success(comments))
    }

    override suspend fun publishPlaceComment(
        placeId: String,
        text: String
    ): Flow<Result<List<PlaceComment>>> = flow {
        if (error.not()){
                comments.add(
                    PlaceComment(
                        id = UUID.randomUUID().toString(),
                        text = text,
                        placeId = placeId,
                        createdDate = System.currentTimeMillis(),
                        author = TestUtils.getRandomUser()
                    )
                )
                emit(Result.success(comments))
        }else{
            emit(Result.failure<List<PlaceComment>>(Exception("Error while adding comment")))
        }
    }
}