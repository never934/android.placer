package com.placer.data.db.place.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.placer.data.db.place.PlaceDB
import com.placer.data.db.user.UserDB
import com.placer.data.db.user.toEntity
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.entity.user.User

@Entity
internal data class PlaceCommentDB(
    @PrimaryKey
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: Long,
    val author: UserDB
)

internal fun List<PlaceCommentDB>.toEntities() : List<PlaceComment> {
    return map {
        it.toEntity()
    }
}

internal fun PlaceCommentDB.toEntity() : PlaceComment {
    return PlaceComment(
        id = id,
        placeId = placeId,
        text = text,
        createdDate = createdDate,
        author = author.toEntity()
    )
}