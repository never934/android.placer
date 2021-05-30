package com.placer.client.interfaces

import com.google.android.gms.maps.GoogleMap

interface LocationPermissions {
    fun isLocationPermissionsGranted() : Boolean
    fun enableMyLocation()
}