package com.placer.data.repository

import com.placer.data.api.AuthApi
import com.placer.data.api.request.AuthRequest
import com.placer.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject internal constructor(
    private val authApi: AuthApi,
    private val dispatcher: CoroutineDispatcher
    ) : AuthRepository {

    override suspend fun signIn(firebaseToken: String): Flow<Result<String>> =
        authApi.signIn(AuthRequest(firebaseToken))
            .map { Result.success(it.token) }
            .catch { Result.failure<String>(Exception("Error while auth")) }
            .flowOn(dispatcher)

}