package com.placer.data.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(
    @PrimaryKey
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