package com.placer.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.placer.data.db.user.UserDB
import java.lang.reflect.Type


internal object UserDBConverter {
    @TypeConverter
    fun storedStringToUser(data: String?): UserDB {
        val gson = Gson()
        val objectType: Type = object : TypeToken<UserDB>() {}.type
        return gson.fromJson(data, objectType)
    }

    @TypeConverter
    fun userToStoredString(user: UserDB): String {
        val gson = Gson()
        return gson.toJson(user)
    }
}