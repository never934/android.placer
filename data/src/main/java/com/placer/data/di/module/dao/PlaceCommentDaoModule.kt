package com.placer.data.di.module.dao

import com.placer.data.db.place.comment.PlaceCommentDao
import com.placer.data.di.module.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlaceCommentDaoModule{
    @Provides
    @Singleton
    internal fun providesPlaceCommentDao(database: AppDatabase): PlaceCommentDao {
        return database.placeCommentDao()
    }
}