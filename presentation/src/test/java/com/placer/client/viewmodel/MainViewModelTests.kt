package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.fake.FakeUserRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.MainViewModel
import com.placer.domain.usecase.user.LoadUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
@ExperimentalCoroutinesApi
internal class MainViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var userRepository: FakeUserRepository

    @Before
    fun setupViewModel() {
        userRepository = FakeUserRepository()
        mainViewModel = MainViewModel(LoadUserUseCase(userRepository, Dispatchers.IO))
    }

    @Test
    fun exitExecuteChanged() = mainCoroutineRule.runBlockingTest {
        // Given

        // When
        mainViewModel.exit()

        // Then
        assertThat(mainViewModel.exitExecute.getOrAwaitValue(), CoreMatchers.`is`(true))
    }

    @Test
    fun exitCompletedExecuteChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        mainViewModel.exit()

        // When
        mainViewModel.exitDone()

        // Then
        assertThat(mainViewModel.exitExecute.getOrAwaitValue(), CoreMatchers.`is`(false))
    }
}