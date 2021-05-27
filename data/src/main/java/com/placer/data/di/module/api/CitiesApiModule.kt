package com.placer.data.di.module.api

import com.placer.data.api.CitiesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CitiesApiModule {
    @Provides
    @Singleton
    internal fun provideCitiesApi(retrofit: Retrofit): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }
}