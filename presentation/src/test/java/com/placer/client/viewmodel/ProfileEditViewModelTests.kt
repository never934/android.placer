package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.entity.toView
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.user.profile.ProfileEditViewModel
import com.placer.data.repository.fake.FakeUserRepository
import com.placer.domain.TestUtils
import com.placer.domain.entity.user.User
import com.placer.domain.usecase.user.LoadUserUseCase
import com.placer.domain.usecase.user.UpdateUserUseCase
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
internal class ProfileEditViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var profileEditViewModel: ProfileEditViewModel
    private lateinit var userRepository: FakeUserRepository
    private lateinit var users: ArrayList<User>

    @Before
    fun setupViewModel() {
        users = arrayListOf(TestUtils.getRandomUser())
        userRepository = FakeUserRepository(users)
        profileEditViewModel = ProfileEditViewModel(
            LoadUserUseCase(userRepository, Dispatchers.IO),
            UpdateUserUseCase(userRepository, Dispatchers.IO)
        )
    }

    @Test
    fun loadProfileSuccess() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoUsers = users

        // When
        profileEditViewModel.loadProfile()

        // Then
        MatcherAssert.assertThat(profileEditViewModel.profile.getOrAwaitValue(), CoreMatchers.`is`(repoUsers[0].toView()))
    }
}