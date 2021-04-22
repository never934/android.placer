package com.placer.data.di.module.dao

import com.placer.data.db.place.PlaceDao
import com.placer.data.di.module.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlaceDaoModule{
    @Provides
    @Singleton
    internal fun providesPlaceDao(database: AppDatabase): PlaceDao {
        return database.placeDao()
    }
}