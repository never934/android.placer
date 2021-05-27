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
internal class LoadPlaceCommentsUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loadPlaceCommentsUseCase: LoadPlaceCommentsUseCase
    private var placeId = UUID.randomUUID().toString()

    @Before
    fun setup() {
        loadPlaceCommentsUseCase = LoadPlaceCommentsUseCase(
            FakePlaceCommentRepository(
                arrayListOf(
                    TestUtils.getRandomPlaceComment(placeId),
                    TestUtils.getRandomPlaceComment(placeId),
                    TestUtils.getRandomPlaceComment(placeId),
                    TestUtils.getRandomPlaceComment(placeId)
                )
            ),
            Dispatchers.IO
        )
    }

    @Test
    fun loadPlaceCommentsSuccessResult() = runBlocking {
        // Given
        val id = placeId

        // When
        val result = loadPlaceCommentsUseCase.loadPlaceComments(id).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, CoreMatchers.`is`(4))
    }
}