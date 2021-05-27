package com.placer.domain.usecase.place

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
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
internal class DeletePlaceUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var deletePlaceUseCase: DeletePlaceUseCase
    private lateinit var placeId: String

    @Before
    fun setup() {
        val place = TestUtils.getRandomPlace()
        deletePlaceUseCase = DeletePlaceUseCase(
            FakePlaceRepository(arrayListOf(place)),
            Dispatchers.IO
        )
        placeId = place.id
    }

    @Test
    fun deletePlaceSuccessResult() = runBlocking {
        // Given
        val id = placeId

        // When
        val result = deletePlaceUseCase.deletePlace(id).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull(), CoreMatchers.`is`(true))
    }
}