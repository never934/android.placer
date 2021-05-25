package com.placer.client.interfaces

import com.google.android.gms.maps.GoogleMap

interface MyLocation {
    fun isLocationPermissionsGranted() : Boolean
    fun enableMyLocation()
}