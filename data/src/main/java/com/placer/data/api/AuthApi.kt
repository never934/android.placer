package com.placer.data.api

import com.placer.data.api.request.AuthRequest
import com.placer.data.api.response.AuthResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApi {
    @POST("v1/auth/signIn")
    suspend fun signIn(@Body request: AuthRequest) : AuthResponse
}