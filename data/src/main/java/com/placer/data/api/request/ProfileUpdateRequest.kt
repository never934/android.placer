package com.placer.data.api.request

internal data class ProfileUpdateRequest(
    val name: String,
    val nickname: String? = null
)