package com.placer.data.repository

import android.util.Log
import com.placer.data.AppPrefs
import com.placer.data.api.UserApi
import com.placer.data.api.request.ProfileUpdateRequest
import com.placer.data.api.response.CityResponse
import com.placer.data.api.response.toDB
import com.placer.data.db.user.UserDao
import com.placer.data.db.user.toEntities
import com.placer.data.db.user.toEntity
import com.placer.data.utils.Extensions.toMultipartPhoto
import com.placer.domain.entity.city.City
import com.placer.domain.entity.user.User
import com.placer.domain.repository.PlaceRepository
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher
) : UserRepository {
    override suspend fun loadUsers(): Flow<Result<List<User>>> = flow {
        try {
            val users = userApi.getUsers()
            val daoUsers = userDao.updateUsers(users.toDB())
            emit(Result.success(daoUsers.toEntities()))
        }catch (e: Exception){
            emit(Result.success(userDao.getUsers().toEntities()))
        }
    }
        .flowOn(dispatcher)

    override suspend fun loadUsersFromCache(): Flow<Result<List<User>>> = flow {
        emit(Result.success(userDao.getUsers().toEntities()))
    }
        .flowOn(dispatcher)

    override suspend fun loadUser(userId: String): Flow<Result<User>> = flow {
        try {
            val userResponse = userApi.getUser(userId)
            val daoUser = userDao.updateUser(userId, userResponse.toDB())
            emit(Result.success(daoUser.toEntity()))
        }catch (e: Exception){
            emit(Result.success(userDao.getUser(userId).toEntity()))
        }
    }
        .flowOn(dispatcher)

    override suspend fun loadProfile(): Flow<Result<User>> =
        flow {
            try {
                val user = userApi.getProfile()
                val userDb = userDao.updateUser(user.id, user.toDB())
                emit(Result.success(userDb.toEntity()))
            }catch (e: Exception){
                emit(Result.success(userDao.getUser(AppPrefs.getUserId()).toEntity()))
            }
        }
            .catch { Result.failure<User>(Exception("Error while loading user")) }
            .flowOn(dispatcher)


    override suspend fun updateUser(user: User): Flow<Result<User>> = flow {
        try {
            val userResponse = userApi.updateProfile(ProfileUpdateRequest(user.name, user.nickname))
            val daoUser = userDao.updateUser(user.id, userResponse.toDB())
            emit(Result.success(daoUser.toEntity()))
        }catch (e: Exception){
            emit(Result.success(userDao.getUser(user.id).toEntity()))
        }
    }
        .flowOn(dispatcher)

    override suspend fun updateUserCity(userId: String, city: City): Flow<Result<User>> = flow{
        try {
            val user = userApi.updateProfileCity(CityResponse(city.city, city.country, city.latitude, city.longitude))
            val daoUser = userDao.updateUser(user.id, user.toDB())
            emit(Result.success(daoUser.toEntity()))
        }catch (e: Exception){
            emit(Result.success(userDao.getUser(userId).toEntity()))
        }
    }
        .flowOn(dispatcher)

    override suspend fun updateUserAvatar(userId: String, avatar: ByteArray): Flow<Result<User>> = flow {
        try {
            val user = userApi.updateProfileAvatar(avatar.toMultipartPhoto("file"))
            val daoUser = userDao.updateUser(user.id, user.toDB())
            emit(Result.success(daoUser.toEntity()))
        }catch (e: Exception){
            emit(Result.success(userDao.getUser(userId).toEntity()))
        }
    }
        .flowOn(dispatcher)
}