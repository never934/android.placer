package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.TestUtils
import com.placer.client.entity.toView
import com.placer.client.fake.FakePlaceRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.top.PlacesTopViewModel
import com.placer.domain.entity.place.Place
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
internal class PlacesTopViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var placesTopViewModel: PlacesTopViewModel
    private lateinit var placeRepository: FakePlaceRepository
    private lateinit var places: ArrayList<Place>

    @Before
    fun setupViewModel() {
        places = arrayListOf(TestUtils.getRandomPlace(), TestUtils.getRandomPlace(), TestUtils.getRandomPlace())
        placeRepository = FakePlaceRepository(places)
        placesTopViewModel = PlacesTopViewModel(LoadPlacesUseCase(placeRepository, Dispatchers.IO))
    }

    @Test
    fun reloadDataChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        mainCoroutineRule.pauseDispatcher()
        placesTopViewModel.reload()
        mainCoroutineRule.resumeDispatcher()

        // Then
        assertThat(placesTopViewModel.topPlaces.getOrAwaitValue().size, CoreMatchers.`is`(repoPlaces.size))
    }

    @Test
    fun loadPlacesByInputDataChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        placesTopViewModel.loadPlaces(repoPlaces[0].name)

        // Then
        assertThat(placesTopViewModel.topPlaces.getOrAwaitValue().contains(repoPlaces[0].toView()), CoreMatchers.`is`(true))
    }

    @Test
    fun placeClickedGoToPlaceChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        placesTopViewModel.placeClicked(repoPlaces[0].toView())

        // Then
        assertThat(placesTopViewModel.goToPlaceView.getOrAwaitValue(), CoreMatchers.`is`(repoPlaces[0].toView()))
    }

    @Test
    fun navigatedToPlaceGoToPlaceChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        placesTopViewModel.placeClicked(repoPlaces[0].toView())
        placesTopViewModel.navigatedToPlaceView()

        // Then
        assertThat(placesTopViewModel.goToPlaceView.getOrAwaitValue(), CoreMatchers.nullValue())
    }
}