package com.placer.domain.repository

import com.placer.domain.entity.City
import com.placer.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loadUsers() : Flow<Result<List<User>>>
    suspend fun loadUser(userId: String) : Flow<Result<User>>
    suspend fun updateUser(user: User) : Flow<Result<User>>
    suspend fun updateUserCity(userId: String, city: City) : Flow<Result<User>>
    suspend fun updateUserAvatar(userId: String, avatar: ByteArray) : Flow<Result<User>>
}