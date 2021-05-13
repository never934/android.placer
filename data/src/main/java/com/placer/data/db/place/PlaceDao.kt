package com.placer.data.db.place

import androidx.room.*
import com.placer.data.api.response.PlaceResponse
import com.placer.domain.entity.user.User


@Dao
interface PlaceDao {

    @Transaction
    suspend fun updatePlaces(places: List<PlaceDB>) : List<PlaceDB> {
        deletePlaces()
        savePlaces(places)
        return getPlaces()
    }

    @Transaction
    suspend fun updateUserPlaces(userId: String, places: List<PlaceDB>) : List<PlaceDB> {
        val userOldPlaces = getPlaces().filter { it.author.id == userId }
        deletePlaces(userOldPlaces.toTypedArray())
        savePlaces(places)
        return getPlaces().filter { it.author.id == userId }
    }

    @Query("SELECT * FROM placedb")
    suspend fun getPlaces(): List<PlaceDB>

    @Query("SELECT * FROM placedb where id = :placeId")
    suspend fun getPlace(placeId: String) : PlaceDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlace(place: PlaceDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlaces(places: List<PlaceDB>)

    @Query("DELETE FROM placedb")
    suspend fun deletePlaces()

    @Delete
    suspend fun deletePlaces(places: Array<PlaceDB>)

    @Query("DELETE FROM placedb where id = :placeId")
    suspend fun deletePlace(placeId: String)

    @Update
    suspend fun updatePlace(place: PlaceDB)
}