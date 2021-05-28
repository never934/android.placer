package com.placer.client.repo

import com.placer.client.TestUtils
import com.placer.client.wrapEspressoIdlingResource
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.repository.PlaceCommentRepository
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.collections.ArrayList

internal class FakePlaceCommentRepository(private val comments: ArrayList<PlaceComment> = arrayListOf()) : PlaceCommentRepository {

    var error = false

    override suspend fun loadPlaceComments(placeId: String): Flow<Result<List<PlaceComment>>> = wrapEspressoIdlingResource {
        flow {
            emit(Result.success(comments))
        }
    }

    override suspend fun publishPlaceComment(
        placeId: String,
        text: String
    ): Flow<Result<List<PlaceComment>>> = wrapEspressoIdlingResource {
        flow {
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
}