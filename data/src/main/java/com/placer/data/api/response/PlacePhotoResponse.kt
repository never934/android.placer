package com.placer.data.api.response

import com.placer.data.db.place.photo.PlacePhotoDB

internal data class PlacePhotoResponse(
    val id: String,
    val url: String,
    val createdDate: Long
)

internal fun PlacePhotoResponse.toDB() : PlacePhotoDB {
    return PlacePhotoDB(id, url, createdDate)
}