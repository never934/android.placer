package com.placer.data.api.response

import com.placer.data.db.place.comment.PlaceCommentDB

data class PlaceCommentResponse(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: UserResponse
)

fun List<PlaceCommentResponse>.toDB() : List<PlaceCommentDB> {
    return map {
        PlaceCommentDB(
            id = it.id,
            placeId = it.placeId,
            text = it.text,
            createdDate = it.createdDate,
            author = it.author.toDB()
        )
    }
}