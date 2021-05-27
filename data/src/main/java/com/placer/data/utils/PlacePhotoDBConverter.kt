package com.placer.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.placer.data.db.place.photo.PlacePhotoDB

internal object PlacePhotoDBConverter {
    @TypeConverter
    fun storedStringToPlacePhotosDB(data: String): List<PlacePhotoDB> {
        val gson = Gson()
        val listType = object : TypeToken<List<PlacePhotoDB>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun placePhotosDBToStoredString(myObjects: List<PlacePhotoDB>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}