package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.auth.AuthViewModel
import com.placer.data.repository.fake.FakeAuthRepository
import com.placer.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
internal class AuthViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var authRepository: FakeAuthRepository

    @Before
    fun setupViewModel() {
        authRepository = FakeAuthRepository()
        authViewModel = AuthViewModel(SignInUseCase(authRepository, Dispatchers.IO))
    }

    @Test
    fun signInResultSuccess() = mainCoroutineRule.runBlockingTest {
        // Given

        // When
        authViewModel.signIn("")

        // Then
        assertThat(authViewModel.loginSuccessed.getOrAwaitValue(), `is`(true))
        assertThat(authViewModel.userId.getOrAwaitValue(), `is`(""))
    }
}