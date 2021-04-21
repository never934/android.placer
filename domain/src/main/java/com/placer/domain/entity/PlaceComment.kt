package com.placer.domain.entity

data class PlaceComment(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: User
)