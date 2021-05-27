package com.placer.domain.usecase.user

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.entity.user.User
import com.placer.domain.fake.FakeUserRepository
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
internal class LoadUserUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loadUserUseCase: LoadUserUseCase
    private lateinit var users: ArrayList<User>

    @Before
    fun setup() {
        users = arrayListOf(TestUtils.getRandomUser())
        loadUserUseCase = LoadUserUseCase(
            FakeUserRepository(users),
            Dispatchers.IO
        )
    }

    @Test
    fun loadUserSuccessResult() = runBlocking {
        // Given
        val repoUsers = users

        // When
        val result = loadUserUseCase.loadUser(repoUsers[0].id).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.equals(repoUsers[0]), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.id, CoreMatchers.`is`(repoUsers[0].id))
    }

    @Test
    fun loadProfileSuccessResult() = runBlocking {
        // Given

        // When
        val result = loadUserUseCase.loadProfile().first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
    }

}