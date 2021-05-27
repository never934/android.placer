package com.placer.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.MainCoroutineRule
import com.placer.data.TestUtils
import com.placer.data.TestUtils.toRequests
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
        val firstPlace = TestUtils.getRandomPlaceRequest()
        val secondPlace = TestUtils.getRandomPlaceRequest()
        val thirdPlace = TestUtils.getRandomPlaceRequest()

        // When
        placeRepository.publishPlace(firstPlace).collect()
        placeRepository.publishPlace(secondPlace).collect()
        placeRepository.publishPlace(thirdPlace).collect()
        val result = placeRepository.loadPlaces().first()
        val requests = result.getOrNull()?.toRequests()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, `is`(3))
        MatcherAssert.assertThat(requests?.contains(firstPlace), `is`(true))
        MatcherAssert.assertThat(requests?.contains(secondPlace), `is`(true))
        MatcherAssert.assertThat(requests?.contains(thirdPlace), `is`(true))
    }

    @Test
    fun publishPlaceFailure() = runBlocking {
        // Given
        placeRepository.error = true
        val place = TestUtils.getRandomPlaceRequest()

        // When
        val result = placeRepository.publishPlace(place).first()
        val places = placeRepository.loadPlaces().first()
        val requests = places.getOrNull()?.toRequests()

        // Then
        MatcherAssert.assertThat(result.isFailure, `is`(true))
        MatcherAssert.assertThat(places.getOrNull()?.size, `is`(0))
        MatcherAssert.assertThat(requests?.contains(place), `is`(false))
    }

    @Test
    fun getUserPlaces() = runBlocking {
        // Given
        placeRepository.error = false
        val place = TestUtils.getRandomPlaceRequest()

        // When
        val placePublished = placeRepository.publishPlace(place).first()
        val result = placeRepository.loadUserPlaces(placePublished.getOrNull()?.author?.id ?: "").first()
        val resultRequests = result.getOrNull()?.toRequests()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, `is`(1))
        MatcherAssert.assertThat(resultRequests?.contains(place), `is`(true))
    }

    @Test
    fun deletePlaceSuccess() = runBlocking {
        // Given
        placeRepository.error = false
        val place = TestUtils.getRandomPlaceRequest()

        // When
        val publishedPlace = placeRepository.publishPlace(place).first()
        val result = placeRepository.deletePlace(publishedPlace.getOrNull()?.id ?: "").first()
        val places = placeRepository.loadPlaces().first()
        val placesRequests = places.getOrNull()?.toRequests()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull(), `is`(true))
        MatcherAssert.assertThat(placesRequests?.contains(place), `is`(false))
    }

    @Test
    fun deletePlaceFailure() = runBlocking {
        // Given
        placeRepository.error = false
        val place = TestUtils.getRandomPlaceRequest()

        // When
        val publishedPlace = placeRepository.publishPlace(place).first()
        placeRepository.error = true
        val result = placeRepository.deletePlace(publishedPlace.getOrNull()?.id ?: "").first()
        val places = placeRepository.loadPlaces().first()
        val placeRequests = places.getOrNull()?.toRequests()

        // Then
        MatcherAssert.assertThat(result.isFailure, `is`(true))
        MatcherAssert.assertThat(placeRequests?.contains(place), `is`(true))
    }
}