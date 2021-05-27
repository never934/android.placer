package com.placer.domain.usecase.place

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.entity.place.Place
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
internal class LoadPlacesUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loadPlacesUseCase: LoadPlacesUseCase
    private lateinit var places: ArrayList<Place>

    @Before
    fun setup() {
        this.places = arrayListOf(TestUtils.getRandomPlace(), TestUtils.getRandomPlace(), TestUtils.getRandomPlace())
        loadPlacesUseCase = LoadPlacesUseCase(
            FakePlaceRepository(places),
            Dispatchers.IO
        )
    }

    @Test
    fun loadPlacesSuccessResult() = runBlocking {
        // Given
        val repoPlaces = places

        // When
        val result = loadPlacesUseCase.loadPlaces().first()
        val places = result.getOrNull()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(places?.size, CoreMatchers.`is`(repoPlaces.size))
        MatcherAssert.assertThat(places?.contains(repoPlaces[0]), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(places?.contains(repoPlaces[1]), CoreMatchers.`is`(true))
    }

    @Test
    fun loadPlacesByIdSuccessResult() = runBlocking {
        // Given
        val repoPlaces = places

        // When
        val result = loadPlacesUseCase.loadPlaceById(repoPlaces[0].id).first()
        val place = result.getOrNull()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(place?.id, CoreMatchers.`is`(repoPlaces[0].id))
        MatcherAssert.assertThat(place?.equals(repoPlaces[0]), CoreMatchers.`is`(true))
    }

    @Test
    fun loadPlacesByInputFromCacheWithEmptyFilterSuccessResult() = runBlocking {
        // Given
        val repoPlaces = places

        // When
        val resultNotEmpty = loadPlacesUseCase.loadPlacesByInputFromCacheWithEmptyFilter(repoPlaces[0].name).first()
        val resultEmpty = loadPlacesUseCase.loadPlacesByInputFromCacheWithEmptyFilter("").first()

        // Then
        MatcherAssert.assertThat(resultNotEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.getOrNull()?.isEmpty(), CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultNotEmpty.getOrNull()?.contains(repoPlaces[0]), CoreMatchers.`is`(true))
    }

    @Test
    fun loadPlacesByInputFromCacheSuccessResult() = runBlocking {
        // Given
        val repoPlaces = places

        // When
        val resultNotEmpty = loadPlacesUseCase.loadPlacesByInputFromCache(repoPlaces[0].name).first()
        val resultEmpty = loadPlacesUseCase.loadPlacesByInputFromCache("").first()

        // Then
        MatcherAssert.assertThat(resultNotEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.getOrNull()?.size, CoreMatchers.`is`(3))
        MatcherAssert.assertThat(resultNotEmpty.getOrNull()?.contains(repoPlaces[0]), CoreMatchers.`is`(true))
    }

    @Test
    fun loadPlacesByInputForTopSuccessResult() = runBlocking {
        // Given
        val repoPlaces = places

        // When
        val resultNotEmpty = loadPlacesUseCase.loadPlacesByInputForTop(repoPlaces[0].name).first()
        val resultEmpty = loadPlacesUseCase.loadPlacesByInputForTop("").first()

        // Then
        MatcherAssert.assertThat(resultNotEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.getOrNull()?.size, CoreMatchers.`is`(3))
        MatcherAssert.assertThat(resultNotEmpty.getOrNull()?.contains(repoPlaces[0]), CoreMatchers.`is`(true))

        MatcherAssert.assertThat(resultEmpty.getOrNull()?.get(2)?.topPosition ?: 0
            > resultEmpty.getOrNull()?.get(1)?.topPosition ?: 0, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(resultEmpty.getOrNull()?.get(1)?.topPosition ?: 0
            > resultEmpty.getOrNull()?.get(0)?.topPosition ?: 0, CoreMatchers.`is`(true))

        MatcherAssert.assertThat(resultNotEmpty.getOrNull()?.size, CoreMatchers.`is`(1))
    }

    @Test
    fun loadPlacesForTopSuccessResult() = runBlocking {
        // Given
        val repoPlaces = places

        // When
        val result = loadPlacesUseCase.loadPlacesForTop().first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.size, CoreMatchers.`is`(3))

        MatcherAssert.assertThat(result.getOrNull()?.get(2)?.topPosition ?: 0
            > result.getOrNull()?.get(1)?.topPosition ?: 0, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.get(1)?.topPosition ?: 0
            > result.getOrNull()?.get(0)?.topPosition ?: 0, CoreMatchers.`is`(true))
    }
}