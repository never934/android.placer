package com.placer.client.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.placer.client.AppClass

object LocationUtils {
    @SuppressLint("MissingPermission")
    fun getBestLocation(): Location? {
        val mLocationManager = AppClass.appInstance.applicationContext.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val providers = mLocationManager!!.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = mLocationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) { // Found best last known location: %s", l);
                bestLocation = l
            }
        }
        return bestLocation
    }
}