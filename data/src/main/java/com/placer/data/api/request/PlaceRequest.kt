package com.placer.data.api.request

import com.placer.domain.entity.place.Place

data class PlaceRequest(
    val name: String,
    val description: String? = null,
    val lat: Double,
    val lng: Double,
    val published: Boolean
)

fun Place.toRequest() : PlaceRequest {
    return PlaceRequest(
        name = name,
        description = description,
        lat = lat,
        lng = lng,
        published = published
    )
}