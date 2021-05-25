package com.placer.client.interfaces

import com.placer.client.entity.PlaceView

internal interface OnPlaceChosen {
    fun placeChosen(place: PlaceView)
}