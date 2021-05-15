package com.placer.client.entity

import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.util.extensions.DateExtensions.toView
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlacePhoto
import java.util.*

internal data class PlaceView(
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
    val photos: List<PlacePhoto>,
    val createdDate: String
)

internal fun Place.toView() : PlaceView {
    return PlaceView(
        id = id,
        name = name,
        description = description,
        lat = lat,
        lng = lng,
        cityName = cityName ?: AppClass.context.getString(R.string.null_representation),
        published = published,
        author = author.toView(),
        commentsCount = commentsCount,
        topPosition = topPosition,
        photos = photos,
        createdDate = Date(createdDate).toView()
    )
}