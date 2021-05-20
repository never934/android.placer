package com.placer.data.repository

import com.placer.data.AppPrefs
import com.placer.data.api.AuthApi
import com.placer.data.api.request.AuthRequest
import com.placer.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject internal constructor(
    private val authApi: AuthApi,
    private val dispatcher: CoroutineDispatcher
    ) : AuthRepository {

    override suspend fun signIn(firebaseToken: String): Flow<Result<String>> =
        flow{
            try {
                val authResponse = authApi.signIn(AuthRequest(firebaseToken))
                AppPrefs.saveServerToken(authResponse.token)
                AppPrefs.saveUserId(authResponse.userId)
                emit(Result.success(authResponse.userId))
            }catch (e: Exception){
                emit(Result.failure<String>(Exception("Error while auth")))
            }
        }
            .flowOn(dispatcher)

}