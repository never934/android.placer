package com.placer.client.entity

data class PlaceCommentView(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: String,
    val author: UserView
)