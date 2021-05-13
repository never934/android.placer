package com.placer.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.placer.data.db.place.PlaceDB
import com.placer.data.di.module.room.AppDatabase
import com.placer.data.utils.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PlaceDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
            )
            .setTransactionExecutor(
                Executors.newSingleThreadExecutor()
            )
            .build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun updatePlacesAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlace(null)
        val data2 = TestUtils.getRandomPlace(null)
        val data3 = TestUtils.getRandomPlace(null)
        database.placeDao().savePlaces(listOf(data2, data3))

        // When
        val loaded = database.placeDao().updatePlaces(listOf(data))

        // Then
        MatcherAssert.assertThat(loaded.size, `is`(1))
        MatcherAssert.assertThat(loaded.first() == data, CoreMatchers.`is`(true))
    }

    @Test
    fun updateUserPlacesAndGet() = runBlocking {
        // Given
        val userId = UUID.randomUUID().toString()
        val data = TestUtils.getRandomPlace(userId)
        val data2 = TestUtils.getRandomPlace(userId)
        val data3 = TestUtils.getRandomPlace(userId)
        database.placeDao().savePlaces(listOf(data2, data3))

        // When
        val loaded = database.placeDao().updateUserPlaces(userId, listOf(data))

        // Then
        MatcherAssert.assertThat(loaded.size, `is`(1))
        MatcherAssert.assertThat(loaded.first() == data, CoreMatchers.`is`(true))
    }

    @Test
    fun savePlacesAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlace(null)

        // When
        database.placeDao().savePlaces(listOf(data))
        val loaded = database.placeDao().getPlaces()

        // Then
        MatcherAssert.assertThat(loaded.size, `is`(1))
        MatcherAssert.assertThat(loaded.first() == data, `is`(true))
    }

    @Test
    fun savePlaceAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlace(null)
        database.placeDao().savePlace(data)

        // When
        val loaded = database.placeDao().getPlace(data.id)

        // Then
        MatcherAssert.assertThat(loaded == data, `is`(true))
    }

    @Test
    fun deletePlacesAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlace(null)
        database.placeDao().savePlace(data)

        // When
        database.placeDao().deletePlaces()
        val loaded = database.placeDao().getPlaces()

        // Then
        MatcherAssert.assertThat(loaded.isEmpty(), `is`(true))
    }

    @Test
    fun deletePlaceAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlace(null)
        database.placeDao().savePlace(data)

        // When
        database.placeDao().deletePlace(data.id)
        val loaded = database.placeDao().getPlace(data.id)

        // Then
        MatcherAssert.assertThat(loaded, CoreMatchers.nullValue())
    }

    @Test
    fun updatePlaceAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlace(null)
        val updatedPlace =
            PlaceDB(
                id = data.id,
                name = TestUtils.randomString(),
                description = TestUtils.randomString(),
                lat = TestUtils.randomDouble(),
                lng = TestUtils.randomDouble(),
                cityName = TestUtils.randomString(),
                published = TestUtils.randomBoolean(),
                author = TestUtils.getRandomUser(null),
                commentsCount = TestUtils.randomLong(),
                topPosition = TestUtils.randomLong(),
                photos = listOf(),
                createdDate = System.currentTimeMillis()
            )
        database.placeDao().savePlace(data)

        // When
        database.placeDao().updatePlace(updatedPlace)
        val loaded = database.placeDao().getPlace(data.id)

        // Then
        MatcherAssert.assertThat(loaded == updatedPlace, `is`(true))
    }
}