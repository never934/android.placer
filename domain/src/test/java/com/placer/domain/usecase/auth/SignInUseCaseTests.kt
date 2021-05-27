package com.placer.domain.usecase.auth

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.fake.FakeAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SignInUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var signInUseCase: SignInUseCase

    @Before
    fun setup() {
        signInUseCase = SignInUseCase(FakeAuthRepository(), Dispatchers.IO)
    }

    @Test
    fun signInSuccessResult() = runBlocking {
        // Given
        val token = TestUtils.randomString()

        // When
        val result = signInUseCase.signIn(token).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
    }
}