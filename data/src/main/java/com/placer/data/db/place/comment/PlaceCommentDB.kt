package com.placer.data.db.place.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.placer.data.db.place.PlaceDB
import com.placer.data.db.user.UserDB
import com.placer.data.db.user.toEntity
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.entity.user.User

@Entity
data class PlaceCommentDB(
    @PrimaryKey
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: UserDB
)

fun List<PlaceCommentDB>.toEntities() : List<PlaceComment> {
    return map {
        PlaceComment(
            id = it.id,
            placeId = it.placeId,
            text = it.text,
            createdDate = it.createdDate,
            author = it.author.toEntity()
        )
    }
}