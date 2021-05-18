package com.placer.data.api

import com.placer.data.api.request.PlaceCommentRequest
import com.placer.data.api.request.PlaceRequest
import com.placer.data.api.response.PlaceCommentResponse
import com.placer.data.api.response.PlaceResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaceApi {
    /** common **/

    @GET("v1/places/user/{userId}")
    fun getUserPlaces(@Path("userId") userId: String) : Flow<List<PlaceResponse>>

    /** base operations **/

    @GET("v1/places")
    suspend fun getPlaces() : List<PlaceResponse>

    @POST("v1/places")
    fun publishPlace(@Body request: PlaceRequest) : Flow<PlaceResponse>

    @DELETE("v1/places/{placeId}")
    fun deletePlace(@Path("placeId")placeId: String) : Flow<Void>

    @PUT("v1/places/{placeId}")
    fun updatePlace(@Path("placeId")placeId: String, @Body request: PlaceRequest) : Flow<PlaceResponse>

    /** comments **/

    @GET("v1/places/{placeId}/comments")
    fun getPlaceComments(@Path("placeId")placeId: String) : Flow<List<PlaceCommentResponse>>

    @POST("v1/places/{placeId}/comments")
    fun publishPlaceComments(@Path("placeId")placeId: String, @Body comments: List<PlaceCommentRequest>) : Flow<List<PlaceCommentResponse>>

    /** photos **/

    @Multipart
    @POST("v1/places/{placeId}/photos")
    fun publishPlacePhoto(@Path("placeId")placeId: String, @Part file: MultipartBody.Part) : Flow<PlaceResponse>

    @DELETE("v1/places/{placeId}/photos")
    fun deletePlacePhotos(@Path("placeId")placeId: String, @Body photoIds: List<String>) : Flow<PlaceResponse>

}