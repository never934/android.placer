package com.placer.client

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.placer.client.servicelocator.DependencyInstanceImpl
import com.placer.client.servicelocator.ServiceLocator
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

open class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(this)
        DataBunchModule.initDataModule(this)
        ServiceLocator.setDependencyInstance(DependencyInstanceImpl(this))
    }

}