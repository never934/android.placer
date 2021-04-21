package com.placer.client.entity.mappers

import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.entity.PlaceView
import com.placer.domain.entity.place.Place
import com.placer.client.util.extensions.DateExtensions.toView
import com.placer.client.entity.mappers.UserMapper.toView
import java.util.*

object PlaceMapper {
    fun Place.toView() : PlaceView {
        return PlaceView(
            id = id,
            name = name,
            description = description,
            lat = lat,
            lng = lng,
            cityName = cityName ?: AppClass.context.getString(R.string.null_representation),
            published = published,
            author = author.toView(),
            commentsCount = commentsCount,
            topPosition = topPosition,
            photoUrls = photoUrls,
            createdDate = Date(createdDate).toView()
        )
    }
}