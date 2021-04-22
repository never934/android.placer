package com.placer.data.api.request

data class ProfileUpdateRequest(
    val name: String,
    val nickname: String? = null
)