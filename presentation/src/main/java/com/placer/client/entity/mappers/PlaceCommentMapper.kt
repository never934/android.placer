package com.placer.client.entity.mappers

import com.placer.client.entity.PlaceCommentView
import com.placer.domain.entity.place.PlaceComment
import com.placer.client.util.extensions.DateExtensions.toView
import com.placer.client.entity.mappers.UserMapper.toView
import java.util.*

object PlaceCommentMapper {
    fun PlaceComment.toView() : PlaceCommentView {
        return PlaceCommentView(
            id = id,
            text = text,
            placeId = placeId,
            createdDate = Date(createdDate).toView(),
            author = author.toView()
        )
    }
}