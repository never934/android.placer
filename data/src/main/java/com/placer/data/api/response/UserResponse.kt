package com.placer.data.api.response

data class UserResponse(
    val id: String,
    val name: String,
    val nickname: String?,
    val placesCount: Long,
    val topPosition: Long,
    val cityLat: Double,
    val cityLng: Double,
    val cityName: String,
    val avatarUrl: String?,
    val createdDate: Long
)