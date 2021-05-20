package com.placer.client

import android.app.Application
import android.content.Context
import com.google.android.gms.maps.MapsInitializer
import com.placer.data.DataBunchModule
import com.placer.data.di.component.*
import com.placer.data.di.module.api.AuthApiModule
import com.placer.data.di.module.api.CitiesApiModule
import com.placer.data.di.module.api.PlaceApiModule
import com.placer.data.di.module.api.UserApiModule
import com.placer.data.di.module.dao.PlaceCommentDaoModule
import com.placer.data.di.module.dao.PlaceDaoModule
import com.placer.data.di.module.dao.UserDaoModule
import com.placer.data.di.module.retrofit.AuthRetrofitModule
import com.placer.data.di.module.retrofit.RetrofitSettingsModule
import com.placer.data.di.module.retrofit.ServerRetrofitModule
import com.placer.data.di.module.room.RoomModule
import com.placer.data.di.module.usecase.*

class AppClass : Application() {

    lateinit var authComponent: AuthComponent
    lateinit var cityComponent: CityComponent
    lateinit var placeCommentComponent: PlaceCommentComponent
    lateinit var placeComponent: PlaceComponent
    lateinit var placePhotoComponent: PlacePhotoComponent
    lateinit var userComponent: UserComponent

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(this)
        DataBunchModule.initDataModule(this)
        appInstance = this
        setupAuthComponent()
        setupCityComponent()
        setupPlaceCommentComponent()
        setupPlaceComponent()
        setupPlacePhotoComponent()
        setupUserComponent()
    }

    private fun setupAuthComponent(){
        authComponent = DaggerAuthComponent.builder()
            .authApiModule(AuthApiModule())
            .authModule(AuthModule())
            .authRetrofitModule(AuthRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .build()
    }

    private fun setupCityComponent(){
        cityComponent = DaggerCityComponent.builder()
            .citiesApiModule(CitiesApiModule())
            .cityModule(CityModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .build()
    }

    private fun setupPlaceCommentComponent(){
        placeCommentComponent = DaggerPlaceCommentComponent.builder()
            .placeApiModule(PlaceApiModule())
            .placeCommentModule(PlaceCommentModule())
            .placeCommentDaoModule(PlaceCommentDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(this))
            .build()
    }

    private fun setupPlaceComponent(){
        placeComponent = DaggerPlaceComponent.builder()
            .placeApiModule(PlaceApiModule())
            .placeModule(PlaceModule())
            .placeDaoModule(PlaceDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(this))
            .build()
    }

    private fun setupPlacePhotoComponent(){
        placePhotoComponent = DaggerPlacePhotoComponent.builder()
            .placeApiModule(PlaceApiModule())
            .placePhotoModule(PlacePhotoModule())
            .placeDaoModule(PlaceDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(this))
            .build()
    }

    private fun setupUserComponent(){
        userComponent = DaggerUserComponent.builder()
            .userApiModule(UserApiModule())
            .userModule(UserModule())
            .userDaoModule(UserDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(this))
            .build()
    }

    companion object{
        lateinit var appInstance: AppClass
        private set
    }
}