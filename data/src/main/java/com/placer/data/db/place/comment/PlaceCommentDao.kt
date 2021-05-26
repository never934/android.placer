package com.placer.data.db.place.comment

import androidx.room.*

@Dao
interface PlaceCommentDao {

    @Transaction
    suspend fun updatePlaceComments(placeId: String, comments: List<PlaceCommentDB>) : List<PlaceCommentDB> {
        deleteAllCommentsFromPlace(placeId)
        savePlaceComments(comments)
        return getPlaceComments(placeId)
    }

    @Query("SELECT * FROM placecommentdb WHERE placeId = :placeId ORDER BY createdDate DESC")
    suspend fun getPlaceComments(placeId: String): List<PlaceCommentDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlaceComments(placeCommentDB: List<PlaceCommentDB>)

    @Query("DELETE FROM placecommentdb WHERE placeId = :placeId")
    suspend fun deleteAllCommentsFromPlace(placeId: String)
}