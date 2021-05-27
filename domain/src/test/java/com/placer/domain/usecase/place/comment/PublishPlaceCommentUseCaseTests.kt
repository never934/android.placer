package com.placer.domain.usecase.place.comment

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.fake.FakePlaceCommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
internal class PublishPlaceCommentUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var publishPlaceCommentsUseCase: PublishPlaceCommentUseCase

    @Before
    fun setup() {
        publishPlaceCommentsUseCase = PublishPlaceCommentUseCase(
            FakePlaceCommentRepository(),
            Dispatchers.IO
        )
    }

    @Test
    fun loadPlaceCommentsSuccessResult() = runBlocking {
        // Given
        val placeId = UUID.randomUUID().toString()
        val placeComment = TestUtils.randomString()

        // When
        val result = publishPlaceCommentsUseCase.publishPlaceComment(placeId, placeComment).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(result.getOrNull()?.get(0)?.text, CoreMatchers.`is`(placeComment))
    }
}