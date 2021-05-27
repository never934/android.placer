package com.placer.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.MainCoroutineRule
import com.placer.data.TestUtils
import com.placer.data.fake.FakePlaceCommentRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
@ExperimentalCoroutinesApi
class PlaceCommentRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var placeCommentRepository: FakePlaceCommentRepository

    @Before
    fun setup() {
        placeCommentRepository = FakePlaceCommentRepository()
    }

    @Test
    fun publishPlaceCommentSuccessAndGet() = runBlocking {
        // Given
        placeCommentRepository.error = false
        val placeId = UUID.randomUUID().toString()
        val firstText = TestUtils.randomString()
        val secondText = TestUtils.randomString()

        // When
        placeCommentRepository.publishPlaceComment(placeId, firstText).collect()
        placeCommentRepository.publishPlaceComment(placeId, secondText).collect()
        val result = placeCommentRepository.loadPlaceComments(placeId).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, CoreMatchers.`is`(2))
    }

    @Test
    fun publishPlaceCommentFailure() = runBlocking {
        // Given
        placeCommentRepository.error = true

        // When
        val result = placeCommentRepository.publishPlaceComment(UUID.randomUUID().toString(), TestUtils.randomString())

        // Then
        MatcherAssert.assertThat(result.first().isFailure, CoreMatchers.`is`(true))
    }

}