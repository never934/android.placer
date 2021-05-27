package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.fake.FakeAuthRepository
import com.placer.client.fake.FakeCityRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.auth.AuthViewModel
import com.placer.client.screens.city.ChooseCityViewModel
import com.placer.domain.usecase.auth.SignInUseCase
import com.placer.domain.usecase.city.LoadCitiesUseCase
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
class ChooseCityViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var chooseCityViewModel: ChooseCityViewModel

    // Use a fake repository to be injected into the view model.
    private lateinit var cityRepository: FakeCityRepository

    @Before
    fun setupStatisticsViewModel() {
        cityRepository = FakeCityRepository()
        chooseCityViewModel = ChooseCityViewModel(LoadCitiesUseCase(cityRepository, Dispatchers.IO))
    }

    @Test
    fun signInResultSuccess() = mainCoroutineRule.runBlockingTest {
        // Given

        // When
        authViewModel.signIn("")

        // Then
        MatcherAssert.assertThat(authViewModel.loginSuccessed.getOrAwaitValue(), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(authViewModel.userId.getOrAwaitValue(), CoreMatchers.`is`(""))
    }
}