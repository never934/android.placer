package com.placer.client.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface GalleryTransaction {
    fun startGallery(launcher: ActivityResultLauncher<Intent>)
}