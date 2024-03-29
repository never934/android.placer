package com.placer.data.db.place

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.placer.data.api.response.PlaceCommentResponse
import com.placer.data.db.place.comment.PlaceCommentDB
import com.placer.data.db.place.photo.PlacePhotoDB
import com.placer.data.db.place.photo.toEntities
import com.placer.data.db.user.UserDB
import com.placer.data.db.user.toEntity
import com.placer.data.utils.PlacePhotoDBConverter
import com.placer.data.utils.UserDBConverter
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.entity.place.PlacePhoto
import com.placer.domain.entity.user.User

@Entity
internal data class PlaceDB(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String?,
    val published: Boolean,
    val author: UserDB,
    val commentsCount: Long,
    val topPosition: Long,
    val photos: List<PlacePhotoDB>,
    val createdDate: Long
)

internal fun PlaceDB.toEntity() : Place {
    return Place(
        id = id,
        name = name,
        description = description,
        lat = lat,
        lng = lng,
        cityName = cityName,
        published = published,
        author = author.toEntity(),
        commentsCount = commentsCount,
        topPosition = topPosition,
        photos = photos.toEntities(),
        createdDate = createdDate
    )
}

internal fun List<PlaceDB>.toEntities() : List<Place> {
    return map { it.toEntity() }
}