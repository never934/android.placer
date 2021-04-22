package com.placer.data.api.response

import com.placer.domain.entity.user.User

data class PlaceResponse(
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String?,
    val published: Boolean,
    val author: User,
    val commentsCount: Long,
    val topPosition: Long,
    val photos: List<PlacePhotoResponse>,
    val createdDate: Long
)