package com.placer.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(firebaseToken: String) : Flow<Result<String>>
}