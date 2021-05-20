package com.placer.data.repository

import com.placer.data.AppPrefs
import com.placer.data.api.UserApi
import com.placer.data.api.request.ProfileUpdateRequest
import com.placer.data.api.response.CityResponse
import com.placer.data.api.response.toDB
import com.placer.data.db.user.UserDao
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
    override suspend fun loadUsers(): Flow<Result<List<User>>> =
        userApi.getUsers()
            .map { userDao.updateUsers(it.map { userResponse -> userResponse.toDB() }) }
            .map { Result.success(it.map { userDB -> userDB.toEntity() }) }
            .catch { Result.success(userDao.getUsers().map { userDB -> userDB.toEntity() }) }
            .flowOn(dispatcher)

    override suspend fun loadUser(userId: String): Flow<Result<User>> =
        userApi.getUser(userId)
            .map { userDao.updateUser(userId, it.toDB()) }
            .map { Result.success(it.toEntity()) }
            .catch { Result.success(userDao.getUser(userId).toEntity()) }
            .catch { Result.failure<User>(Exception("Error while loading user")) }
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


    override suspend fun updateUser(user: User): Flow<Result<User>> =
        userApi.updateProfile(ProfileUpdateRequest(user.name, user.nickname))
            .map { userDao.updateUser(it.id, it.toDB()) }
            .map { Result.success(it.toEntity()) }
            .map { Result.failure<User>(Exception("Error while user updating")) }
            .flowOn(dispatcher)

    override suspend fun updateUserCity(userId: String, city: City): Flow<Result<User>> =
        userApi.updateProfileCity(CityResponse(city.city, city.latitude, city.longitude))
            .map { userDao.updateUser(it.id, it.toDB()) }
            .map { Result.success(it.toEntity()) }
            .map { Result.failure<User>(Exception("Error while user city updating")) }
            .flowOn(dispatcher)

    override suspend fun updateUserAvatar(userId: String, avatar: ByteArray): Flow<Result<User>> =
        userApi.updateProfileAvatar(avatar.toMultipartPhoto("file"))
            .map { userDao.updateUser(it.id, it.toDB()) }
            .map { Result.success(it.toEntity()) }
            .map { Result.failure<User>(Exception("Error while user avatar updating")) }
            .flowOn(dispatcher)
}