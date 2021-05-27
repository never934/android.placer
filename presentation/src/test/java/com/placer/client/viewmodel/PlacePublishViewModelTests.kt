package com.placer.client.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.client.MainCoroutineRule
import com.placer.client.TestUtils
import com.placer.client.fake.FakePlaceRepository
import com.placer.client.getOrAwaitValue
import com.placer.client.screens.places.publish.PlacePublishViewModel
import com.placer.domain.usecase.place.PublishPlaceUseCase
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
import java.io.ByteArrayInputStream

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
@ExperimentalCoroutinesApi
internal class PlacePublishViewModelTests {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var placePublishViewModel: PlacePublishViewModel
    private lateinit var placeRepository: FakePlaceRepository

    @Before
    fun setupViewModel() {
        placeRepository = FakePlaceRepository()
        placePublishViewModel = PlacePublishViewModel(PublishPlaceUseCase(FakePlaceRepository(), Dispatchers.IO))
    }

    @Test
    fun refreshDataChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val placeName = TestUtils.randomString()
        val placeDescription = TestUtils.randomString()
        val placePrivate = TestUtils.randomBoolean()

        // When
        placePublishViewModel.refreshData(placeName, placeDescription, placePrivate)

        // Then
        MatcherAssert.assertThat(placePublishViewModel.placeName.getOrAwaitValue(), CoreMatchers.`is`(placeName))
        MatcherAssert.assertThat(placePublishViewModel.placeDescription.getOrAwaitValue(), CoreMatchers.`is`(placeDescription))
        MatcherAssert.assertThat(placePublishViewModel.placePrivate.getOrAwaitValue(), CoreMatchers.`is`(placePrivate))
    }

    @Test
    fun pointChosenUsedChanged() = mainCoroutineRule.runBlockingTest {
        // Given
        val photoStream = ByteArrayInputStream(ByteArray(10))

        // When
        placePublishViewModel.decodePhotoFromStream(photoStream)

        // Then
        MatcherAssert.assertThat(placePublishViewModel.placePhoto.getOrAwaitValue(), CoreMatchers.notNullValue())
    }
}