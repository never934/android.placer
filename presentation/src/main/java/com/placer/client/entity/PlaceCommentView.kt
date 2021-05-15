package com.placer.client.entity

import android.content.Context
import com.placer.client.util.extensions.DateExtensions.toView
import com.placer.domain.entity.place.PlaceComment
import java.util.*

internal data class PlaceCommentView(
    val id: String,
    val text: String,
    val placeId: String,
    val createdDate: String,
    val author: UserView
)

internal fun PlaceComment.toView(context: Context) : PlaceCommentView {
    return PlaceCommentView(
        id = id,
        text = text,
        placeId = placeId,
        createdDate = Date(createdDate).toView(),
        author = author.toView(context)
    )
}