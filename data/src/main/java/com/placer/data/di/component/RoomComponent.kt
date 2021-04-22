package com.placer.data.di.component

import com.placer.data.db.place.PlaceDao
import com.placer.data.db.place.comment.PlaceCommentDao
import com.placer.data.db.user.UserDao
import com.placer.data.di.module.dao.PlaceCommentDaoModule
import com.placer.data.di.module.dao.PlaceDaoModule
import com.placer.data.di.module.dao.UserDaoModule
import com.placer.data.di.module.room.RoomModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RoomModule::class,
        PlaceDaoModule::class,
        UserDaoModule::class,
        PlaceCommentDaoModule::class
    ]
)
interface RoomComponent {
    val placeDao: PlaceDao
    val userDao: UserDao
    val placeCommentDao: PlaceCommentDao
}