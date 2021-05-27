package com.placer.data.api.response

import com.placer.data.db.place.PlaceDB
import com.placer.domain.entity.user.User

internal data class PlaceResponse(
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String?,
    val published: Boolean,
    val author: UserResponse,
    val commentsCount: Long,
    val topPosition: Long,
    val photos: List<PlacePhotoResponse>,
    val createdDate: Long
)

internal fun PlaceResponse.toDB() : PlaceDB {
    return PlaceDB(
        id = id,
        name = name,
        description = description,
        lat = lat,
        lng = lng,
        cityName = cityName,
        published = published,
        author = author.toDB(),
        commentsCount = commentsCount,
        topPosition = topPosition,
        photos = photos.map { it.toDB() },
        createdDate = createdDate
    )
}

internal fun List<PlaceResponse>.toDBs() : List<PlaceDB> {
    return map { it.toDB() }
}