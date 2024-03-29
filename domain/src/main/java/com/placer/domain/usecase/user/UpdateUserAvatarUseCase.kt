package com.placer.domain.usecase.user

import com.placer.domain.entity.user.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UpdateUserAvatarUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateUserAvatar(userId: String, avatar: ByteArray) : Flow<Result<User>> =
        userRepository.updateUserAvatar(userId, avatar).flowOn(dispatcher)
}