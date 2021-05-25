package com.placer.client.util.extensions
import android.graphics.Bitmap
import java.nio.ByteBuffer


object BitmapExtensions {
    suspend fun Bitmap.toByteArray() : ByteArray {
        val size: Int = this.rowBytes * this.height
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(size)
        this.copyPixelsToBuffer(byteBuffer)
        return byteBuffer.array()
    }
}