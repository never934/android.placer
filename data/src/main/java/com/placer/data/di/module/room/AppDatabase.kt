package com.placer.data.di.module.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.placer.data.db.place.PlaceDB
import com.placer.data.db.place.PlaceDao
import com.placer.data.db.place.comment.PlaceCommentDB
import com.placer.data.db.place.comment.PlaceCommentDao
import com.placer.data.db.place.photo.PlacePhotoDB
import com.placer.data.db.user.UserDB
import com.placer.data.db.user.UserDao
import com.placer.data.utils.PlacePhotoDBConverter
import com.placer.data.utils.UserDBConverter

@Database(entities = [PlaceDB::class, UserDB::class, PlaceCommentDB::class], version = 1, exportSchema = false)
@TypeConverters(PlacePhotoDBConverter::class, UserDBConverter::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun placeDao(): PlaceDao
    abstract fun placeCommentDao() : PlaceCommentDao
}