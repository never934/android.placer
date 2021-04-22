package com.placer.data.api

import com.placer.data.api.response.CityResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("v1/cities")
    fun getCities(@Query("input")input: String) : Flow<List<CityResponse>>
}