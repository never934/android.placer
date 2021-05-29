package com.placer.data.repository.fake

import com.placer.data.utils.EspressoIdlingResource.wrapEspressoIdlingResource
import com.placer.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAuthRepository : AuthRepository {

    var error = false

    override suspend fun signIn(firebaseToken: String): Flow<Result<String>> = wrapEspressoIdlingResource {
        flow {
            if (error.not()) {
                emit(Result.success(""))
            } else {
                emit(Result.failure<String>(Exception("Error while signing in")))
            }
        }
    }
}