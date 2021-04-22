package com.placer.data.di.module.dao

import com.placer.data.db.user.UserDao
import com.placer.data.di.module.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserDaoModule {
    @Provides
    @Singleton
    internal fun providesUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}