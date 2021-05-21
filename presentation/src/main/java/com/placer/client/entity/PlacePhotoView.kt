package com.placer.client.entity

import android.os.Parcelable
import com.placer.client.util.extensions.DateExtensions.toView
import com.placer.domain.entity.place.PlacePhoto
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
internal data class PlacePhotoView(
    val id: String,
    val url: String,
    val createdDate: String
) : Parcelable

internal fun PlacePhoto.toView() : PlacePhotoView {
    return PlacePhotoView(
        id = id,
        url = url,
        createdDate = Date(createdDate*1000).toView()
    )
}

internal fun List<PlacePhoto>.toViews() : List<PlacePhotoView> {
    return map { it.toView() }
}