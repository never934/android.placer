package com.placer.client.entity

import android.content.Context
import com.placer.client.util.extensions.DateExtensions.daysFromItRepresentation
import com.placer.domain.entity.user.User
import java.util.*

internal data class UserView(
    val id: String,
    val name: String,
    val nickname: String?,
    val placesCount: Long,
    val topPosition: Long,
    val cityLat: Double,
    val cityLng: Double,
    val cityName: String,
    val avatarUrl: String?,
    val createdDate: String
)

internal fun User.toView(context: Context) : UserView {
    return UserView(
        id = id,
        name = name,
        nickname = nickname,
        placesCount = placesCount,
        topPosition = topPosition,
        cityLat = cityLat,
        cityLng = cityLng,
        cityName = cityName,
        avatarUrl = avatarUrl,
        createdDate = Date(createdDate).daysFromItRepresentation(context)
    )
}