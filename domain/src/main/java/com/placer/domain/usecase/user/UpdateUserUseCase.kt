package com.placer.domain.usecase.user

import com.placer.domain.entity.user.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UpdateUserUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateUser(userName: String, userNickname: String) : Flow<Result<User>> =
        userRepository.updateUser(userName, userNickname).flowOn(dispatcher)
}