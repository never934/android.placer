package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.entity.toView
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.top.UsersTopViewModel
import com.placer.data.repository.fake.FakeUserRepository
import com.placer.domain.TestUtils
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
internal class UsersTopViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var usersTopViewModel: UsersTopViewModel
    private lateinit var userRepository: FakeUserRepository
    private lateinit var users: ArrayList<User>

    @Before
    fun setupViewModel() {
        users = arrayListOf(TestUtils.getRandomUser(), TestUtils.getRandomUser(), TestUtils.getRandomUser())
        userRepository = FakeUserRepository(users)
        usersTopViewModel = UsersTopViewModel(
            LoadUsersUseCase(userRepository, Dispatchers.IO)
        )
    }

    @Test
    fun reloadSuccess() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoUsers = users

        // When
        usersTopViewModel.reload()

        // Then
        MatcherAssert.assertThat(usersTopViewModel.topUsers.getOrAwaitValue().size, CoreMatchers.`is`(repoUsers.size))
        MatcherAssert.assertThat(usersTopViewModel.topUsers.getOrAwaitValue().contains(repoUsers[0].toView()), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(usersTopViewModel.topUsers.getOrAwaitValue().contains(repoUsers[1].toView()), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(usersTopViewModel.topUsers.getOrAwaitValue().contains(repoUsers[2].toView()), CoreMatchers.`is`(true))
    }

    @Test
    fun userClickedGoToUserViewChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoUsers = users

        // When
        usersTopViewModel.userClicked(repoUsers[0].toView())

        // Then
        MatcherAssert.assertThat(usersTopViewModel.goToUserView.getOrAwaitValue(), CoreMatchers.`is`(repoUsers[0].toView()))
    }

    @Test
    fun goToUserViewExecutedGoToUserViewChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoUsers = users
        usersTopViewModel.userClicked(repoUsers[0].toView())

        // When
        usersTopViewModel.goToUserViewExecuted()

        // Then
        MatcherAssert.assertThat(usersTopViewModel.goToUserView.getOrAwaitValue(), CoreMatchers.nullValue())
    }
}