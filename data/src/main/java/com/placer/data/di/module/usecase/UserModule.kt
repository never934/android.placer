package com.placer.data.di.module.usecase

import com.placer.data.api.UserApi
import com.placer.data.db.user.UserDao
import com.placer.data.repository.UserRepositoryImpl
import com.placer.data.repository.fake.FakeUserRepository
import com.placer.domain.repository.UserRepository
import com.placer.domain.usecase.user.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class UserModule(
    private val state: ModuleState,
    private val userRepository: UserRepository? = null
    ) {

    @Provides
    @Singleton
    fun providesLoadUsersUseCase(
        userRepository: UserRepository
    ): LoadUsersUseCase {
        return LoadUsersUseCase(userRepository, Dispatchers.IO)
    }
    @Provides
    @Singleton
    fun providesLoadUserUseCase(
        userRepository: UserRepository
    ): LoadUserUseCase {
        return LoadUserUseCase(userRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesUpdateUserAvatarUseCase(
        userRepository: UserRepository
    ): UpdateUserAvatarUseCase {
        return UpdateUserAvatarUseCase(userRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesUpdateUserCityUseCase(
        userRepository: UserRepository
    ): UpdateUserCityUseCase {
        return UpdateUserCityUseCase(userRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providesUpdateUserUseCase(
        userRepository: UserRepository
    ): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    internal fun providesUserRepository(
        userApi: UserApi, userDao: UserDao
    ): UserRepository {
        return when(state){
            ModuleState.TEST -> userRepository ?: FakeUserRepository()
            ModuleState.RUN -> UserRepositoryImpl(userApi, userDao, Dispatchers.IO)
        }
    }
}