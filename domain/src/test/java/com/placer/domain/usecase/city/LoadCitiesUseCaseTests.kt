package com.placer.domain.usecase.city

import com.placer.domain.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.domain.fake.FakeCityRepository
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
internal class LoadCitiesUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loadCitiesUseCase: LoadCitiesUseCase

    @Before
    fun setup() {
        loadCitiesUseCase = LoadCitiesUseCase(FakeCityRepository(), Dispatchers.IO)
    }

    @Test
    fun loadCitiesSuccessResult() = runBlocking {
        // Given
        val input = TestUtils.randomString()

        // When
        val result = loadCitiesUseCase.loadCities(input).first()

        // Then
        MatcherAssert.assertThat(result.isSuccess, CoreMatchers.`is`(true))
    }
}