package com.placer.client.util

import com.placer.data.AppPrefs
import com.placer.domain.entity.place.Place

object Filters {
    fun getAllMapPointsFilter(place: Place) : Boolean {
        return true
    }

    fun getMyPointsFilter(place: Place) : Boolean {
        return place.author.id == AppPrefs.getUserId()
    }
}