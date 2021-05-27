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
internal class LoadUsersUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loadUsersUseCase: LoadUsersUseCase
    private lateinit var users: ArrayList<User>

    @Before
    fun setup() {
        users = arrayListOf(TestUtils.getRandomUser(), TestUtils.getRandomUser(), TestUtils.getRandomUser())
        loadUsersUseCase = LoadUsersUseCase(
            FakeUserRepository(users),
            Dispatchers.IO
        )
    }

    @Test
    fun loadUsersSuccessResult() = runBlocking {
        // Given
        val repoUsers = users

        // When
        val result = loadUsersUseCase.loadUsers().first()
        val usersResult = result.getOrNull()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(usersResult?.size, CoreMatchers.`is`(3))
        MatcherAssert.assertThat(usersResult?.contains(repoUsers[0]), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(usersResult?.contains(repoUsers[1]), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(usersResult?.contains(repoUsers[2]), CoreMatchers.`is`(true))
    }

    @Test
    fun loadUsersByInputForTopSuccessResult() = runBlocking {
        // Given
        val repoUsers = users

        // When
        val resultNotEmpty = loadUsersUseCase.loadUsersByInputForTop(repoUsers[0].name).first()
        val resultEmpty = loadUsersUseCase.loadUsersByInputForTop("").first()

        // Then
        MatcherAssert.assertThat(resultNotEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.getOrNull()?.size, CoreMatchers.`is`(repoUsers.size))
        MatcherAssert.assertThat(resultNotEmpty.getOrNull()?.contains(repoUsers[0]), CoreMatchers.`is`(true))

        MatcherAssert.assertThat(resultEmpty.getOrNull()?.get(2)?.topPosition ?: 0
            > resultEmpty.getOrNull()?.get(1)?.topPosition ?: 0, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.getOrNull()?.get(1)?.topPosition ?: 0
            > resultEmpty.getOrNull()?.get(0)?.topPosition ?: 0, CoreMatchers.`is`(true))

        MatcherAssert.assertThat(resultNotEmpty.getOrNull()?.size, CoreMatchers.`is`(1))
    }

    @Test
    fun loadUsersForTopSuccessResult() = runBlocking {
        // Given
        val repoUsers = users

        // When
        val result = loadUsersUseCase.loadUsersForTop().first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, CoreMatchers.`is`(repoUsers.size))
        MatcherAssert.assertThat(result.getOrNull()?.get(2)?.topPosition ?: 0
            > result.getOrNull()?.get(1)?.topPosition ?: 0, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.get(1)?.topPosition ?: 0
            > result.getOrNull()?.get(0)?.topPosition ?: 0, CoreMatchers.`is`(true))
    }
}