package com.placer.domain.usecase.place

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.entity.place.Place
import com.placer.domain.fake.FakePlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LoadUserPlacesUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loadUserPlacesUseCase: LoadUserPlacesUseCase
    private lateinit var places: ArrayList<Place>

    @Before
    fun setup() {
        this.places = arrayListOf(TestUtils.getRandomPlace(), TestUtils.getRandomPlace(), TestUtils.getRandomPlace())
        loadUserPlacesUseCase = LoadUserPlacesUseCase(
            FakePlaceRepository(places),
            Dispatchers.IO
        )
    }

    @Test
    fun loadUserPlacesResultSuccess() = runBlocking{
        // Given
        val repoPlaces = places

        // When
        val result = loadUserPlacesUseCase.loadUserPlaces(repoPlaces[0].author.id).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, `is`(true))
        MatcherAssert.assertThat(result.getOrNull()?.get(0)?.equals(repoPlaces[0]), `is`(true))
    }
}