package com.placer.client.entity

import android.os.Parcelable
import com.placer.client.util.extensions.DateExtensions.daysFromItRepresentation
import com.placer.domain.entity.user.User
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
internal data class UserView(
    val id: String,
    val name: String,
    val nickname: String?,
    val placesCount: String,
    val topPosition: Long,
    val cityLat: Double,
    val cityLng: Double,
    val cityName: String,
    val avatarUrl: String?,
    val registrated: Boolean,
    val createdDate: String
) : Parcelable

internal fun User.toView() : UserView {
    return UserView(
        id = id,
        name = name,
        nickname = nickname,
        placesCount = placesCount.toString(),
        topPosition = topPosition,
        cityLat = cityLat,
        cityLng = cityLng,
        cityName = cityName,
        avatarUrl = avatarUrl,
        registrated = registrated,
        createdDate = Date(createdDate*1000).daysFromItRepresentation()
    )
}

internal fun List<User>.toViews() : List<UserView> {
    return map { it.toView() }
}