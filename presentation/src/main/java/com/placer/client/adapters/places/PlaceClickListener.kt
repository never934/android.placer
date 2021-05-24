package com.placer.client.adapters.places

import com.placer.client.entity.PlaceView

internal class PlaceClickListener(val clickListener: (place: PlaceView) -> Unit) {
    fun onClick(place: PlaceView) = clickListener(place)
}