package com.placer.domain.repository

import com.placer.domain.entity.City
import com.placer.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loadUsers() : Flow<List<User>>
    suspend fun loadUser(userId: String) : Flow<User>
    suspend fun updateUser(user: User) : Flow<User>
    suspend fun updateUserCity(userId: String, city: City) : Flow<User>
    suspend fun updateUserAvatar(userId: String, avatar: ByteArray) : Flow<User>
}