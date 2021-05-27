package com.placer.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.MainCoroutineRule
import com.placer.data.TestUtils
import com.placer.data.fake.FakeAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
@ExperimentalCoroutinesApi
class AuthResponseRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var authRepository: FakeAuthRepository

    @Before
    fun setup() {
        authRepository = FakeAuthRepository()
    }

    @Test
    fun signInSuccessResult() = runBlocking {
        // Given
        authRepository.error = false

        // When
        val result = authRepository.signIn(TestUtils.randomString())

        // Then
        MatcherAssert.assertThat(result.first().isSuccess, `is`(true))
    }

    @Test
    fun signInFailureResult() = runBlocking {
        // Given
        authRepository.error = true

        // When
        val result = authRepository.signIn(TestUtils.randomString())

        // Then
        MatcherAssert.assertThat(result.first().isFailure, `is`(true))
    }

}