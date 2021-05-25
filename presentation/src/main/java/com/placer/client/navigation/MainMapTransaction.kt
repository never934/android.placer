package com.placer.client.navigation

import com.placer.client.entity.PlaceView

internal interface MainMapTransaction {
    interface WithPlace{
        fun setMainMapFragment(place: PlaceView)
    }
    interface WithoutPlace{
        fun setMainMapFragment()
    }
}