package com.placer.data.db.place.photo

import com.placer.domain.entity.place.PlacePhoto

internal data class PlacePhotoDB(
    val id: String,
    val url: String,
    val createdDate: Long
)

internal fun List<PlacePhotoDB>.toEntities() : List<PlacePhoto> {
    return map {
        PlacePhoto(
            id = it.id,
            url = it.url,
            createdDate = it.createdDate
        )
    }
}