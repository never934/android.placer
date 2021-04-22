package com.placer.data.di.module.api

import com.placer.data.api.PlaceApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PlaceApiModule {
    @Provides
    @Singleton
    fun providePlaceApi(retrofit: Retrofit): PlaceApi {
        return retrofit.create(PlaceApi::class.java)
    }
}