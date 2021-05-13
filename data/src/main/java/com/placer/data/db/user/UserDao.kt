package com.placer.data.db.user

import androidx.room.*

@Dao
interface UserDao {

    @Transaction
    suspend fun updateUsers(users: List<UserDB>) : List<UserDB> {
        deleteUsers()
        saveUsers(users)
        return getUsers()
    }

    @Transaction
    suspend fun updateUser(userId: String, user: UserDB) : UserDB {
        saveUser(user)
        return getUser(userId)
    }

    @Query("SELECT * FROM userdb")
    suspend fun getUsers(): List<UserDB>

    @Query("SELECT * FROM userdb where id = :userId")
    suspend fun getUser(userId: String): UserDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(users: List<UserDB>)

    @Query("DELETE FROM userdb")
    suspend fun deleteUsers()
}