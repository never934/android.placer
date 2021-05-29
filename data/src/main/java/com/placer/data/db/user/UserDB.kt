package com.placer.data.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.placer.domain.entity.user.User

@Entity
internal data class UserDB(
    @PrimaryKey
    val id: String,
    val name: String,
    val nickname: String?,
    val placesCount: Long,
    val topPosition: Long,
    val cityLat: Double?,
    val cityLng: Double?,
    val cityName: String?,
    val avatarUrl: String?,
    val registrated: Boolean,
    val createdDate: Long
)

internal fun UserDB.toEntity() : User {
    return User(
        id = id,
        name = name,
        nickname = nickname,
        placesCount = placesCount,
        topPosition = topPosition,
        cityLat = cityLat,
        cityLng = cityLng,
        cityName = cityName,
        avatarUrl = avatarUrl,
        registrated = registrated,
        createdDate = createdDate
    )
}

internal fun List<UserDB>.toEntities() : List<User> {
    return map { it.toEntity() }
}