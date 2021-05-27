package com.placer.data.di.module.usecase

import com.placer.data.api.AuthApi
import com.placer.data.repository.AuthRepositoryImpl
import com.placer.domain.repository.AuthRepository
import com.placer.domain.usecase.auth.SignInUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides
    @Singleton
    fun providesSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi, Dispatchers.IO)
    }
}