package com.placer.client.fake

import com.placer.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FakeAuthRepository : AuthRepository {

    var error = false

    override suspend fun signIn(firebaseToken: String): Flow<Result<String>> = flow {
        if (error.not()){
            emit(Result.success(""))
        }else{
            emit(Result.failure<String>(Exception("Error while signing in")))
        }
    }
}