package com.placer.data.di.module.room

import android.app.Application
import androidx.room.Room
import com.placer.data.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val app: Application){
    @Provides
    @Singleton
    internal fun providesRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(app,
            AppDatabase::class.java,
            Constants.DB_NAME)
            .build()
    }
}