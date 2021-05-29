package com.placer.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.data.repository.fake.FakeUserRepository
import com.placer.domain.entity.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
internal class UserRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var userRepository: FakeUserRepository
    private lateinit var firstUser: User
    private lateinit var secondUser: User
    private lateinit var thirdUser: User

    @Before
    fun setup() {
        firstUser = TestUtils.getRandomUser()
        secondUser = TestUtils.getRandomUser()
        thirdUser = TestUtils.getRandomUser()
        userRepository = FakeUserRepository(arrayListOf(firstUser, secondUser, thirdUser))
    }

    @Test
    fun loadSimpleUserSuccess() = runBlocking {
        // Given
        userRepository.error = false

        // When
        val result = userRepository.loadUser(firstUser.id).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.equals(firstUser), `is`(true))
    }

    @Test
    fun loadSimpleUserFailure() = runBlocking {
        // Given
        userRepository.error = true

        // When
        val result = userRepository.loadUser(firstUser.id).first()

        // Then
        MatcherAssert.assertThat(result.isFailure, CoreMatchers.`is`(true))
    }

    @Test
    fun loadAllUsers() = runBlocking {
        // Given
        userRepository.error = false

        // When
        val result = userRepository.loadUsers().first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.contains(firstUser), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.contains(secondUser), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.contains(thirdUser), CoreMatchers.`is`(true))
    }

}