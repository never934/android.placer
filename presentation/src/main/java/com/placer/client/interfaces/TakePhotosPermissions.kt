package com.placer.client.interfaces

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.placer.client.servicelocator.ServiceLocator

interface TakePhotosPermissions {
    fun isTakePhotosPermissionsGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                ServiceLocator.instance.context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}