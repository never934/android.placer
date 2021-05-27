package com.placer.data.api.response

import com.placer.data.db.place.comment.PlaceCommentDB

internal data class PlaceCommentResponse(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: UserResponse
)

internal fun List<PlaceCommentResponse>.toDB() : List<PlaceCommentDB> {
    return map {
        it.toDB()
    }
}

internal fun PlaceCommentResponse.toDB() : PlaceCommentDB {
    return PlaceCommentDB(
        id = id,
        placeId = placeId,
        text = text,
        createdDate = createdDate,
        author = author.toDB()
    )
}