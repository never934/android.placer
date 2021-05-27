package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.placer.client.MainCoroutineRule
import com.placer.client.TestUtils
import com.placer.client.fake.FakePlaceRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.places.publish.PlaceChoosePointViewModel
import com.placer.domain.usecase.place.LoadPlacesUseCase
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
class PlaceChoosePointViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var placeChoosePointViewModel: PlaceChoosePointViewModel
    private lateinit var placeRepository: FakePlaceRepository

    @Before
    fun setupViewModel() {
        placeRepository = FakePlaceRepository()
        placeChoosePointViewModel = PlaceChoosePointViewModel(LoadPlacesUseCase(placeRepository, Dispatchers.IO))
    }

    @Test
    fun pointChosenChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val latLng = LatLng(TestUtils.randomDouble(), TestUtils.randomDouble())

        // When
        placeChoosePointViewModel.pointChosen(latLng)

        // Then
        assertThat(placeChoosePointViewModel.pointChosen.getOrAwaitValue(), CoreMatchers.`is`(latLng))
    }

    @Test
    fun pointChosenUsedChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val latLng = LatLng(TestUtils.randomDouble(), TestUtils.randomDouble())
        placeChoosePointViewModel.pointChosen(latLng)

        // When
        placeChoosePointViewModel.pointChosenUsed()

        // Then
        assertThat(placeChoosePointViewModel.pointChosen.getOrAwaitValue(), CoreMatchers.nullValue())
    }
}