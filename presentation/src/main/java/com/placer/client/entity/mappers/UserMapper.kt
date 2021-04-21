package com.placer.client.entity.mappers

import com.placer.client.entity.UserView
import com.placer.domain.entity.user.User
import com.placer.client.util.extensions.DateExtensions.daysFromItRepresentation
import java.util.*

object UserMapper {
    fun User.toView() : UserView {
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
            createdDate = Date(createdDate).daysFromItRepresentation()
        )
    }
}