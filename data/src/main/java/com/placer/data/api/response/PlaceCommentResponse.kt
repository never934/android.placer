package com.placer.data.api.response

data class PlaceCommentResponse(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: UserResponse
)