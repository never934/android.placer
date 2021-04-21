package com.placer.domain.entity.place

import com.placer.domain.entity.user.User

data class PlaceComment(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: User
)