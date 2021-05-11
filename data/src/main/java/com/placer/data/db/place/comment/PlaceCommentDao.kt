package com.placer.data.db.place.comment

import androidx.room.*

@Dao
interface PlaceCommentDao {

    @Transaction
    suspend fun updatePlaceComments(placeId: String, comments: List<PlaceCommentDB>){
        deleteAllCommentsFromPlace(placeId)
        savePlaceComments(comments)
    }

    @Query("SELECT * FROM placecommentdb where placeId = :placeId")
    suspend fun getPlaceComments(placeId: String): List<PlaceCommentDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlaceComments(placeCommentDB: List<PlaceCommentDB>)

    @Query("DELETE FROM placecommentdb where placeId = :placeId")
    suspend fun deleteAllCommentsFromPlace(placeId: String)
}