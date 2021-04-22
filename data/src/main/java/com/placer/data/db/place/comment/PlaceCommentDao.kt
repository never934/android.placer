package com.placer.data.db.place.comment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceCommentDao {

    @Query("SELECT * FROM placecommentdb where placeId = :placeId")
    suspend fun getPlaceComments(placeId: String): List<PlaceCommentDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlaceComments(placeCommentDB: List<PlaceCommentDB>)

    @Query("DELETE FROM placecommentdb where placeId = :placeId")
    suspend fun deleteAllCommentsFromPlace(placeId: String)
}