package com.placer.client.entity

import com.placer.domain.entity.place.PlacePhoto

data class PlaceView(
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String,
    val published: Boolean,
    val author: UserView,
    val commentsCount: Long,
    val topPosition: Long,
    val photos: List<PlacePhoto>,
    val createdDate: String
)