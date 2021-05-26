package com.placer.domain.entity.place

data class PlaceRequest(
    val name: String,
    val description: String? = null,
    val lat: Double,
    val lng: Double,
    val published: Boolean
)