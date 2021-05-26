package com.placer.client.interfaces

import com.google.android.gms.maps.model.LatLng

interface PlacePublishTransaction {
    fun setPlacePublishFragment(latLng: LatLng)
}