package com.placer.domain.usecase.user

import com.placer.domain.entity.City
import com.placer.domain.entity.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UpdateUserCityUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateUserCity(userId: String, city: City) : Flow<Result<User>> =
        userRepository.updateUserCity(userId, city).flowOn(dispatcher)
}