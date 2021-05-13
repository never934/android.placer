package com.placer.data.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

internal object Extensions {
    fun ByteArray.toMultipartPhoto(name: String) : MultipartBody.Part {
        val reqFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), this)
        return MultipartBody.Part.createFormData(
            name,
            "$name.jpg",
            reqFile
        )
    }
}