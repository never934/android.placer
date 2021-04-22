package com.placer.data.api.request

data class PlaceRequest(
    val name: String,
    val description: String? = null,
    val lat: Double,
    val lng: Double,
    val published: Boolean
)