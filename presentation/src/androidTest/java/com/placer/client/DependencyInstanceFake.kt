package com.placer.client

import android.app.Application
import android.content.Context
import com.placer.client.servicelocator.DependencyInstance
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
import com.placer.domain.repository.*

class DependencyInstanceFake(
    override val application: Application,
    private val authRepository: AuthRepository? = null,
    private val cityRepository: CityRepository? = null,
    private val placeCommentRepository: PlaceCommentRepository? = null,
    private val placeRepository: PlaceRepository? = null,
    private val userRepository: UserRepository? = null
    ) : DependencyInstance {

    private lateinit var _authComponent: AuthComponent
    override val authComponent: AuthComponent
        get() = _authComponent

    private lateinit var _cityComponent: CityComponent
    override val cityComponent: CityComponent
        get() = _cityComponent

    private lateinit var _placeCommentComponent: PlaceCommentComponent
    override val placeCommentComponent: PlaceCommentComponent
        get() = _placeCommentComponent

    private lateinit var _placeComponent: PlaceComponent
    override val placeComponent: PlaceComponent
        get() = _placeComponent

    private lateinit var _placePhotoComponent: PlacePhotoComponent
    override val placePhotoComponent: PlacePhotoComponent
        get() = _placePhotoComponent

    private lateinit var _userComponent: UserComponent
    override val userComponent: UserComponent
        get() = _userComponent

    override val context: Context
        get() = application.applicationContext

    init {
        setupAuthComponent()
        setupCityComponent()
        setupPlaceCommentComponent()
        setupPlaceComponent()
        setupPlacePhotoComponent()
        setupUserComponent()
    }

    private fun setupAuthComponent() {
        _authComponent = DaggerAuthComponent.builder()
            .authApiModule(AuthApiModule())
            .authModule(AuthModule(ModuleState.TEST, authRepository))
            .authRetrofitModule(AuthRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .build()
    }

    private fun setupCityComponent() {
        _cityComponent = DaggerCityComponent.builder()
            .citiesApiModule(CitiesApiModule())
            .cityModule(CityModule(ModuleState.TEST, cityRepository))
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .build()
    }

    private fun setupPlaceCommentComponent() {
        _placeCommentComponent = DaggerPlaceCommentComponent.builder()
            .placeApiModule(PlaceApiModule())
            .placeCommentModule(PlaceCommentModule(ModuleState.TEST, placeCommentRepository))
            .placeCommentDaoModule(PlaceCommentDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(application))
            .build()
    }

    private fun setupPlaceComponent() {
        _placeComponent = DaggerPlaceComponent.builder()
            .placeApiModule(PlaceApiModule())
            .placeModule(PlaceModule(ModuleState.TEST, placeRepository))
            .placeDaoModule(PlaceDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(application))
            .build()
    }

    private fun setupPlacePhotoComponent() {
        _placePhotoComponent = DaggerPlacePhotoComponent.builder()
            .placeApiModule(PlaceApiModule())
            .placePhotoModule(PlacePhotoModule())
            .placeDaoModule(PlaceDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(application))
            .build()
    }

    private fun setupUserComponent() {
        _userComponent = DaggerUserComponent.builder()
            .userApiModule(UserApiModule())
            .userModule(UserModule(ModuleState.TEST, userRepository))
            .userDaoModule(UserDaoModule())
            .serverRetrofitModule(ServerRetrofitModule())
            .retrofitSettingsModule(RetrofitSettingsModule())
            .roomModule(RoomModule(application))
            .build()
    }
}