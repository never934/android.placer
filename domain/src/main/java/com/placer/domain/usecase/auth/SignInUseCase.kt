package com.placer.domain.usecase.auth

import com.placer.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun signIn(firebaseToken: String) : Flow<Result<String?>> =
        authRepository.signIn(firebaseToken).flowOn(dispatcher)
}