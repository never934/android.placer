package com.placer.data.api.response

import com.placer.data.db.user.UserDB

data class UserResponse(
    val id: String,
    val name: String,
    val nickname: String?,
    val placesCount: Long,
    val topPosition: Long,
    val cityLat: Double,
    val cityLng: Double,
    val cityName: String,
    val avatarUrl: String?,
    val createdDate: Long
)

fun UserResponse.toDB() : UserDB {
    return UserDB(
        id = id,
        name = name,
        nickname = nickname,
        placesCount = placesCount,
        topPosition = topPosition,
        cityLat = cityLat,
        cityLng = cityLng,
        cityName = cityName,
        avatarUrl = avatarUrl,
        createdDate = createdDate
    )
}

fun List<UserResponse>.toDB() : List<UserDB> {
    return map { it.toDB() }
}