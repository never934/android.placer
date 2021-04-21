package com.placer.domain.entity

data class Place(
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
    val photoUrls: List<String>,
    val createdDate: Long
)