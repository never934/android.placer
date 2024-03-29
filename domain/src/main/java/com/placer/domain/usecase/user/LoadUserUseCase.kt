package com.placer.domain.usecase.user

import com.placer.domain.entity.user.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoadUserUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadUser(userId: String) : Flow<Result<User>> =
        userRepository.loadUser(userId).flowOn(dispatcher)

    suspend fun loadProfile() : Flow<Result<User>> =
        userRepository.loadProfile().flowOn(dispatcher)
}