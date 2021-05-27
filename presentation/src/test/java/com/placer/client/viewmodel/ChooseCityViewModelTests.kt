package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.fake.FakeCityRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.city.ChooseCityViewModel
import com.placer.domain.usecase.city.LoadCitiesUseCase
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
internal class ChooseCityViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var chooseCityViewModel: ChooseCityViewModel
    private lateinit var cityRepository: FakeCityRepository

    @Before
    fun setupViewModel() {
        cityRepository = FakeCityRepository()
        chooseCityViewModel = ChooseCityViewModel(LoadCitiesUseCase(cityRepository, Dispatchers.IO))
    }

    @Test
    fun loadCitiesSuccessResult() = mainCoroutineRule.runBlockingTest {
        // Given

        // When
        chooseCityViewModel.loadCities("")

        // Then
        assertThat(chooseCityViewModel.cities.getOrAwaitValue(), CoreMatchers.`is`(listOf()))
    }
}