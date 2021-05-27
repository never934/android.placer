package com.placer.domain.usecase.place

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.TestUtils.toRequest
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
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
internal class UpdatePlaceUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var updatePlaceUseCase: UpdatePlaceUseCase
    private lateinit var places: ArrayList<Place>

    @Before
    fun setup() {
        this.places = arrayListOf(TestUtils.getRandomPlace(), TestUtils.getRandomPlace(), TestUtils.getRandomPlace())
        updatePlaceUseCase = UpdatePlaceUseCase(
            FakePlaceRepository(places),
            Dispatchers.IO
        )
    }

    @Test
    fun updatePlaceResultSuccess() = runBlocking{
        // Given
        val repoPlace = places[0]
        val updatedPlace = PlaceRequest(
            name = "new name",
            description = "new desc",
            lat = repoPlace.lat,
            lng = repoPlace.lng,
            published = repoPlace.published,
        )

        // When
        val result = updatePlaceUseCase.updatePlace(repoPlace.id, updatedPlace).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.toRequest()?.equals(updatedPlace), CoreMatchers.`is`(true))
    }
}