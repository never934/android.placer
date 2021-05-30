package com.placer.client.navigation

import com.google.android.gms.maps.model.LatLng

interface PlacePublishTransaction {
    fun setPlacePublishFragment(latLng: LatLng)
}