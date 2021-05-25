package com.placer.data.api

import com.placer.data.api.request.ProfileUpdateRequest
import com.placer.data.api.response.CityResponse
import com.placer.data.api.response.UserResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {
    @GET("v1/users")
    suspend fun getUsers() : List<UserResponse>

    @PUT("v1/users/profile")
    fun updateProfile(@Body request: ProfileUpdateRequest) : Flow<UserResponse>

    @POST("v1/users/profile/city")
    fun updateProfileCity(@Body response: CityResponse) : Flow<UserResponse>

    @Multipart
    @POST("v1/users/profile/avatar")
    fun updateProfileAvatar(@Part file: MultipartBody.Part) : Flow<UserResponse>

    @GET("v1/users/{userId}")
    suspend fun getUser(@Path("userId")userId: String) : UserResponse

    @GET("v1/users/profile")
    suspend fun getProfile() : UserResponse
}