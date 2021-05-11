package com.placer.data.repository

import com.placer.data.api.UserApi
import com.placer.data.db.user.UserDao
import com.placer.domain.entity.city.City
import com.placer.domain.entity.user.User
import com.placer.domain.repository.PlaceRepository
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher
) : UserRepository {
    override suspend fun loadUsers(): Flow<Result<List<User>>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadUser(userId: String): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserCity(userId: String, city: City): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAvatar(userId: String, avatar: ByteArray): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

}