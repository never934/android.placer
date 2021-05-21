package com.placer.client.interfaces

import com.placer.client.entity.PlaceView

internal interface MainFieldListener {
    fun textInMainFieldChanged(text: String)
    fun mainFieldFocusChanged(hasFocus: Boolean)
    fun showMyPoints()
    fun showAllPoints()
    fun placeSelected(place: PlaceView)
}