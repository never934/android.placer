package com.placer.data.fake

import com.placer.domain.entity.city.City
import com.placer.domain.entity.user.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository(private val _users: List<User>) : UserRepository {

    var error = false
    private val users: ArrayList<User> = ArrayList(_users)

    override suspend fun loadUsers(): Flow<Result<List<User>>> = flow {
        emit(Result.success(users))
    }

    override suspend fun loadUsersFromCache(): Flow<Result<List<User>>> = flow {
        emit(Result.success(users))
    }

    override suspend fun loadUser(userId: String): Flow<Result<User>>  = flow {
        if(error.not()){
            emit(Result.success(users.first { it.id == userId }))
        }else{
            emit(Result.failure<User>(Exception("Error while loading user")))
        }
    }

    override suspend fun loadProfile(): Flow<Result<User>> = flow{
        // server logic (data val from backend)
    }

    override suspend fun updateUser(userName: String, userNickname: String): Flow<Result<User>> = flow {
        // server logic (data val from backend)
    }

    override suspend fun updateUserCity(userId: String, city: City): Flow<Result<User>>  = flow {
        // server logic (data val from backend)
    }

    override suspend fun updateUserAvatar(userId: String, avatar: ByteArray): Flow<Result<User>> = flow {
        // server logic (data val from backend)
    }
}