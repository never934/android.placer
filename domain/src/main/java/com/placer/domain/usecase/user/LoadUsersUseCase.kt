package com.placer.domain.usecase.user

import com.placer.domain.entity.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoadUsersUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadUsers() : Flow<List<User>> =
        userRepository.loadUsers().flowOn(dispatcher)
}