package com.placer.client.util.extensions
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


object BitmapExtensions {
    suspend fun Bitmap.toByteArray() : ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }
}