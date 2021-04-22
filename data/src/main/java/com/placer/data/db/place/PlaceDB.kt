package com.placer.data.db.place

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.placer.domain.entity.place.PlacePhoto
import com.placer.domain.entity.user.User

@Entity
class PlaceDB(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String?,
    val published: Boolean,
    val author: User,
    val commentsCount: Long,
    val topPosition: Long,
    val photos: List<PlacePhoto>,
    val createdDate: Long
)