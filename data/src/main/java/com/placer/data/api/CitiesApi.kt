package com.placer.data.api

import com.placer.data.api.response.CityResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CitiesApi {
    @GET("v1/cities")
    suspend fun getCities(@Query("input")input: String) : List<CityResponse>
}