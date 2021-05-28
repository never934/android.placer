package com.placer.data.di.module.usecase

import com.placer.data.api.AuthApi
import com.placer.data.repository.AuthRepositoryImpl
import com.placer.data.repository.fake.FakeAuthRepository
import com.placer.domain.repository.AuthRepository
import com.placer.domain.usecase.auth.SignInUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AuthModule(
    private val state: ModuleState,
    private val authRepository: AuthRepository? = null
) {

    @Provides
    @Singleton
    fun providesSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesAuthRepository(authApi: AuthApi): AuthRepository {
        return when(state){
            ModuleState.TEST -> authRepository ?: FakeAuthRepository()
            ModuleState.RUN -> AuthRepositoryImpl(authApi, Dispatchers.IO)
        }
    }
}