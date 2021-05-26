package com.placer.data.api

import com.placer.data.api.request.PlaceCommentRequest
import com.placer.data.api.response.PlaceCommentResponse
import com.placer.data.api.response.PlaceResponse
import com.placer.domain.entity.place.PlaceRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaceApi {
    /** common **/

    @GET("v1/places/user/{userId}")
    suspend fun getUserPlaces(@Path("userId") userId: String) : List<PlaceResponse>

    /** base operations **/

    @GET("v1/places")
    suspend fun getPlaces() : List<PlaceResponse>

    @GET("v1/places/{placeId}")
    suspend fun getPlaceById(@Path("placeId")placeId: String) : PlaceResponse

    @POST("v1/places")
    suspend fun publishPlace(@Body request: PlaceRequest) : PlaceResponse

    @DELETE("v1/places/{placeId}")
    suspend fun deletePlace(@Path("placeId")placeId: String)

    @PUT("v1/places/{placeId}")
    suspend fun updatePlace(@Path("placeId")placeId: String, @Body request: PlaceRequest) : PlaceResponse

    /** comments **/

    @GET("v1/places/{placeId}/comments")
    suspend fun getPlaceComments(@Path("placeId")placeId: String) : List<PlaceCommentResponse>

    @POST("v1/places/{placeId}/comments")
    suspend fun publishPlaceComments(@Path("placeId")placeId: String, @Body comments: List<PlaceCommentRequest>) : List<PlaceCommentResponse>

    /** photos **/

    @Multipart
    @POST("v1/places/{placeId}/photos")
    suspend fun publishPlacePhoto(@Path("placeId")placeId: String, @Part file: MultipartBody.Part) : PlaceResponse

    @DELETE("v1/places/{placeId}/photos")
    suspend fun deletePlacePhotos(@Path("placeId")placeId: String, @Body photoIds: List<String>) : PlaceResponse

}