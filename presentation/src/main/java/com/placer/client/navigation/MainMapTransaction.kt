package com.placer.client.navigation

import com.placer.client.entity.PlaceView

internal interface MainMapTransaction {
    fun setMainMapFragment(place: PlaceView)
}