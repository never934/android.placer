package com.placer.domain.usecase.user

import com.placer.domain.entity.user.User
import com.placer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.util.*

class LoadUsersUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadUsers() : Flow<Result<List<User>>> =
        userRepository.loadUsers().flowOn(dispatcher)

    suspend fun loadUsersByInputForTop(input: String): Flow<Result<List<User>>> =
        userRepository.loadUsersFromCache().map {
            it.getOrNull()?.let { list ->
                Result.success(
                    list.filter { user ->
                        (user.nickname?.toUpperCase(Locale.getDefault())?.contains(input.toUpperCase(Locale.getDefault())) ?: false) ||
                        user.name.toUpperCase(Locale.getDefault()).contains(input.toUpperCase(Locale.getDefault())) ||
                        input.isEmpty()
                    }.sortedBy { user -> user.topPosition }.reversed()
                )
            } ?: run {
                it
            }
        }
}