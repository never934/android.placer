package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.TestUtils
import com.placer.client.entity.toView
import com.placer.client.fake.FakePlaceRepository
import com.placer.client.fake.FakeUserRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.top.UsersTopViewModel
import com.placer.client.screens.user.UserViewViewModel
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.place.LoadPlacesUseCase
import com.placer.domain.usecase.place.LoadUserPlacesUseCase
import com.placer.domain.usecase.user.LoadUserUseCase
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
@Config(sdk = [Build.VERSION_CODES.Q])
@ExperimentalCoroutinesApi
class UserViewViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var userViewViewModel: UserViewViewModel
    private lateinit var userRepository: FakeUserRepository
    private lateinit var placeRepository: FakePlaceRepository
    private lateinit var users: ArrayList<User>

    @Before
    fun setupViewModel() {
        users = arrayListOf(TestUtils.getRandomUser())
        userRepository = FakeUserRepository(users)
        placeRepository = FakePlaceRepository()
        userViewViewModel = UserViewViewModel(
            LoadUserPlacesUseCase(placeRepository, Dispatchers.IO),
            LoadUserUseCase(userRepository, Dispatchers.IO),
            users[0].id
        )
    }

    @Test
    fun reloadPlacesAndUserChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoUsers = users

        // When
        userViewViewModel.reload()

        // Then
        MatcherAssert.assertThat(userViewViewModel.user.getOrAwaitValue(), CoreMatchers.`is`(repoUsers[0].toView()))
        MatcherAssert.assertThat(userViewViewModel.places.getOrAwaitValue(), CoreMatchers.`is`(listOf()))
    }
}