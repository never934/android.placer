package com.placer.client.navigation

import com.placer.client.entity.PlaceView

internal interface PlaceViewTransaction {
    fun setPlaceViewFragment(place: PlaceView)
}