package com.placer.domain.repository

import com.placer.domain.entity.city.City
import com.placer.domain.entity.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loadUsers() : Flow<Result<List<User>>>
    suspend fun loadUsersFromCache() : Flow<Result<List<User>>>
    suspend fun loadUser(userId: String) : Flow<Result<User>>
    suspend fun loadProfile() : Flow<Result<User>>
    suspend fun updateUser(userName: String, userNickname: String) : Flow<Result<User>>
    suspend fun updateUserCity(userId: String, city: City) : Flow<Result<User>>
    suspend fun updateUserAvatar(userId: String, avatar: ByteArray) : Flow<Result<User>>
    suspend fun sendFcmToken(token: String) : Flow<Result<Any>>
}