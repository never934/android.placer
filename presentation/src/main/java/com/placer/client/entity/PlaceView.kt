package com.placer.client.entity

import android.content.Context
import android.os.Parcelable
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.util.extensions.DateExtensions.toView
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlacePhoto
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
internal data class PlaceView(
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String,
    val published: Boolean,
    val author: UserView,
    val commentsCount: String,
    val topPosition: String,
    val photos: List<PlacePhotoView>,
    val createdDate: String
) : Parcelable

internal fun Place.toView() : PlaceView {
    return PlaceView(
        id = id,
        name = name,
        description = description,
        lat = lat,
        lng = lng,
        cityName = cityName ?: AppClass.appInstance.applicationContext.getString(R.string.null_representation),
        published = published,
        author = author.toView(),
        commentsCount = commentsCount.toString(),
        topPosition = String.format(AppClass.appInstance.getString(R.string.place_view_in_top_content_format), topPosition),
        photos = photos.toViews(),
        createdDate = Date(createdDate*1000).toView()
    )
}