package com.placer.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.MainCoroutineRule
import com.placer.domain.TestUtils
import com.placer.data.repository.fake.FakeCityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
internal class CityRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var cityRepository: FakeCityRepository

    @Before
    fun setup() {
        cityRepository = FakeCityRepository()
    }

    @Test
    fun getCitiesSuccessResult() = runBlocking {
        // Given
        cityRepository.error = false

        // When
        val result = cityRepository.loadCities(TestUtils.randomString())

        // Then
        MatcherAssert.assertThat(result.first().isSuccess, CoreMatchers.`is`(true))
        MatcherAssert.assertThat(result.first().getOrNull(), CoreMatchers.`is`(listOf()))
    }

    @Test
    fun getCitiesFailureResult() = runBlocking {
        // Given
        cityRepository.error = true

        // When
        val result = cityRepository.loadCities(TestUtils.randomString())

        // Then
        MatcherAssert.assertThat(result.first().isFailure, CoreMatchers.`is`(true))
    }

}