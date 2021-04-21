package com.placer.client.entity

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
    val photoUrls: List<String>,
    val createdDate: String
)