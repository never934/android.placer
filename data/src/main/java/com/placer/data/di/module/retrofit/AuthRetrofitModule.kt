package com.placer.data.di.module.retrofit

import com.placer.data.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module
class AuthRetrofitModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(Constants.BASE_URL).build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        sslSocketFactory: SSLSocketFactory,
        trustManager: X509TrustManager,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(Constants.CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(Constants.READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.sslSocketFactory(sslSocketFactory, trustManager)


        httpClientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader(Constants.CONTENT_TYPE, "application/json")
                .build()
            chain.proceed(request)
        }
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }
}