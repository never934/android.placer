package com.placer.domain.usecase.place

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.TestUtils.toRequest
import com.placer.domain.fake.FakePlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class PublishPlaceUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var publishPlaceUseCase: PublishPlaceUseCase

    @Before
    fun setup() {
        publishPlaceUseCase = PublishPlaceUseCase(
            FakePlaceRepository(),
            Dispatchers.IO
        )
    }

    @Test
    fun publishPlaceResultSuccess() = runBlocking{
        // Given
        val placeRequest = TestUtils.getRandomPlaceRequest()

        // When
        val result = publishPlaceUseCase.publishPlace(placeRequest).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.toRequest(), CoreMatchers.`is`(placeRequest))
    }
}