package com.placer.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.MainCoroutineRule
import com.placer.data.TestUtils
import com.placer.data.fake.FakePlaceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
@ExperimentalCoroutinesApi
class PlaceRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var placeRepository: FakePlaceRepository

    @Before
    fun setup() {
        placeRepository = FakePlaceRepository()
    }

    @Test
    fun publishAndLoadPlaces() = runBlocking {
        // Given
        placeRepository.error = false
        val firstPlace = TestUtils.getRandomPlace()
        val secondPlace = TestUtils.getRandomPlace()
        val thirdPlace = TestUtils.getRandomPlace()

        // When
        placeRepository.publishPlace(firstPlace).collect()
        placeRepository.publishPlace(secondPlace).collect()
        placeRepository.publishPlace(thirdPlace).collect()
        val result = placeRepository.loadPlaces().first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, `is`(3))
        MatcherAssert.assertThat(result.getOrNull()?.contains(firstPlace), `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.contains(secondPlace), `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.contains(thirdPlace), `is`(true))
    }

    @Test
    fun publishPlaceFailure() = runBlocking {
        // Given
        placeRepository.error = true
        val place = TestUtils.getRandomPlace()

        // When
        val result = placeRepository.publishPlace(place).first()
        val places = placeRepository.loadPlaces().first()

        // Then
        MatcherAssert.assertThat(result.isFailure, `is`(true))
        MatcherAssert.assertThat(places.getOrNull()?.size, `is`(0))
        MatcherAssert.assertThat(places.getOrNull()?.contains(place), `is`(false))
    }

    @Test
    fun getUserPlaces() = runBlocking {
        // Given
        placeRepository.error = false
        val place = TestUtils.getRandomPlace()

        // When
        placeRepository.publishPlace(place).collect()
        val result = placeRepository.loadUserPlaces(place.author.id).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, `is`(1))
        MatcherAssert.assertThat(result.getOrNull()?.contains(place), `is`(true))
    }

    @Test
    fun deletePlaceSuccess() = runBlocking {
        // Given
        placeRepository.error = false
        val place = TestUtils.getRandomPlace()

        // When
        placeRepository.publishPlace(place).collect()
        val result = placeRepository.deletePlace(place.id).first()
        val places = placeRepository.loadPlaces().first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull(), `is`(true))
        MatcherAssert.assertThat(places.getOrNull()?.contains(place), `is`(false))
    }

    @Test
    fun deletePlaceFailure() = runBlocking {
        // Given
        placeRepository.error = false
        val place = TestUtils.getRandomPlace()

        // When
        placeRepository.publishPlace(place).collect()
        placeRepository.error = true
        val result = placeRepository.deletePlace(place.id).first()
        val places = placeRepository.loadPlaces().first()

        // Then
        MatcherAssert.assertThat(result.isFailure, `is`(true))
        MatcherAssert.assertThat(places.getOrNull()?.contains(place), `is`(true))
    }
}