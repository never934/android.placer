package com.placer.data.db.place.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.placer.domain.entity.user.User

@Entity
data class PlaceCommentDB(
    @PrimaryKey
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: User
)