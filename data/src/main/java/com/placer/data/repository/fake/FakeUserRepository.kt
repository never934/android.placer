package com.placer.data.repository.fake

import com.placer.data.TestUtils
import com.placer.data.utils.EspressoIdlingResource.wrapEspressoIdlingResource
import com.placer.domain.entity.city.City
import com.placer.domain.entity.user.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FakeUserRepository(private val users: ArrayList<User> = arrayListOf()) : UserRepository {

    var error = false

    override suspend fun loadUsers(): Flow<Result<List<User>>> = wrapEspressoIdlingResource {
        flow {
            emit(Result.success(users))
        }
    }

    override suspend fun loadUsersFromCache(): Flow<Result<List<User>>> = wrapEspressoIdlingResource {
        flow {
            emit(Result.success(users))
        }
    }

    override suspend fun loadUser(userId: String): Flow<Result<User>>  = wrapEspressoIdlingResource {
        flow {
            if(error.not()){
                emit(Result.success(users.first { it.id == userId }))
            }else{
                emit(Result.failure<User>(Exception("Error while loading user")))
            }
        }
    }

    override suspend fun loadProfile(): Flow<Result<User>> = wrapEspressoIdlingResource {
        flow{
            if(error.not()){
                if(users.isNotEmpty()){
                    emit(Result.success(users[0]))
                }else{
                    emit(Result.success(TestUtils.getRandomUser()))
                }
            }else{
                emit(Result.failure<User>(Exception("Error while loading profile")))
            }
        }
    }

    override suspend fun updateUser(userName: String, userNickname: String): Flow<Result<User>> = wrapEspressoIdlingResource {
        flow {
            // server logic (data val from backend)
        }
    }

    override suspend fun updateUserCity(userId: String, city: City): Flow<Result<User>>  = wrapEspressoIdlingResource {
        flow {
            // server logic (data val from backend)
        }
    }

    override suspend fun updateUserAvatar(userId: String, avatar: ByteArray): Flow<Result<User>> = wrapEspressoIdlingResource {
        flow {
            // server logic (data val from backend)
        }
    }
}