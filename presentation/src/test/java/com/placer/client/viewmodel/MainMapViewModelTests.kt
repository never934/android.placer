package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.entity.toView
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.main.MainMapViewModel
import com.placer.data.repository.fake.FakePlaceRepository
import com.placer.domain.TestUtils
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
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
internal class MainMapViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainMapViewModel: MainMapViewModel
    private lateinit var placeRepository: FakePlaceRepository
    private lateinit var places: ArrayList<Place>

    @Before
    fun setupViewModel() {
        this.places = arrayListOf(TestUtils.getRandomPlace(), TestUtils.getRandomPlace(), TestUtils.getRandomPlace())
        placeRepository = FakePlaceRepository(places)
        mainMapViewModel = MainMapViewModel(LoadPlacesUseCase(placeRepository, Dispatchers.IO))
    }

    @Test
    fun loadPlacesByInputSuccessResult() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        mainMapViewModel.loadPlaces(repoPlaces[0].name)

        // Then
        assertThat(mainMapViewModel.searchPlaces.getOrAwaitValue().isNotEmpty(), CoreMatchers.`is`(true))
    }

    @Test
    fun loadMapPlacesSuccessResult() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        mainMapViewModel.loadMapPlaces()

        // Then
        assertThat(mainMapViewModel.mapPlaces.getOrAwaitValue().size, CoreMatchers.`is`(repoPlaces.size))
    }

    @Test
    fun placeClickedGoToPlaceViewExecuted() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        mainMapViewModel.placeClicked(repoPlaces[0].toView())

        // Then
        assertThat(mainMapViewModel.goToPlaceView.getOrAwaitValue()?.equals(repoPlaces[0].toView()), CoreMatchers.`is`(true))
    }

    @Test
    fun placeIdClickedGoToPlaceViewExecuted() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places

        // When
        mainMapViewModel.loadMapPlaces()
        assertThat(mainMapViewModel.mapPlaces.getOrAwaitValue().size, CoreMatchers.`is`(repoPlaces.size))
        mainMapViewModel.placeClicked(repoPlaces[0].id)

        // Then
        assertThat(mainMapViewModel.goToPlaceView.getOrAwaitValue()?.equals(repoPlaces[0].toView()), CoreMatchers.`is`(true))
    }

    @Test
    fun navigatedToPlaceViewNullValue() = mainCoroutineRule.runBlockingTest {
        // Given
        val repoPlaces = places
        mainMapViewModel.loadMapPlaces()
        assertThat(mainMapViewModel.mapPlaces.getOrAwaitValue().size, CoreMatchers.`is`(repoPlaces.size))
        mainMapViewModel.placeClicked(repoPlaces[0].id)

        // When
        mainMapViewModel.navigatedToPlaceView()

        // Then
        assertThat(mainMapViewModel.goToPlaceView.getOrAwaitValue(), CoreMatchers.nullValue())
    }
}