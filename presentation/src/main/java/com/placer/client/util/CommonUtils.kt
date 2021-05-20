package com.placer.client.util

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.placer.data.AppPrefs
import com.placer.domain.entity.place.Place


object CommonUtils {
    fun getScreenHeight() : Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun getScreenWidth() : Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getMapFocusPoint(map: GoogleMap, screen: View, marker: Marker) : LatLng {
        val projection: Projection = map.projection
        val markerPosition: LatLng = marker.position
        val markerPoint: Point = projection.toScreenLocation(markerPosition)
        val targetPoint = Point(markerPoint.x, markerPoint.y - screen.height / 4)
        return projection.fromScreenLocation(targetPoint)
    }
}