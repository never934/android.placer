package com.placer.data.api

import com.placer.data.api.request.ProfileUpdateRequest
import com.placer.data.api.response.CityResponse
import com.placer.data.api.response.UserResponse
import com.placer.domain.entity.user.UserFcmRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.http.*

internal interface UserApi {
    @GET("v1/users")
    suspend fun getUsers() : List<UserResponse>

    @PUT("v1/users/profile")
    suspend fun updateProfile(@Body request: ProfileUpdateRequest) : UserResponse

    @POST("v1/users/profile/city")
    suspend fun updateProfileCity(@Body response: CityResponse) : UserResponse

    @Multipart
    @POST("v1/users/profile/avatar")
    suspend fun updateProfileAvatar(@Part file: MultipartBody.Part) : UserResponse

    @GET("v1/users/{userId}")
    suspend fun getUser(@Path("userId")userId: String) : UserResponse

    @GET("v1/users/profile")
    suspend fun getProfile() : UserResponse

    @POST("v1/users/profile/fcm")
    suspend fun postFcmToken(@Body request: UserFcmRequest) : Any
}