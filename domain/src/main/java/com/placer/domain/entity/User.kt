package com.placer.domain.entity

data class User(
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