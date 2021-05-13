package com.placer.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.placer.data.db.place.comment.PlaceCommentDB
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
class PlaceCommentDaoTest {

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
    fun insertPlaceCommentsAndGetByPlaceId() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlaceCommentDB(null)
        database.placeCommentDao().savePlaceComments(listOf(data))

        // When
        val loaded = database.placeCommentDao().getPlaceComments(data.placeId).firstOrNull()

        // Then
        MatcherAssert.assertThat(loaded as PlaceCommentDB, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, `is`(data.id))
        MatcherAssert.assertThat(loaded.placeId, `is`(data.placeId))
        MatcherAssert.assertThat(loaded.author, `is`(data.author))
        MatcherAssert.assertThat(loaded.createdDate, `is`(data.createdDate))
        MatcherAssert.assertThat(loaded.text, `is`(data.text))
    }

    @Test
    fun insertPlaceCommentsAndDeleteAllFromPlace() = runBlocking {
        // Given
        val data = TestUtils.getRandomPlaceCommentDB(null)
        database.placeCommentDao().savePlaceComments(listOf(data))

        // When
        database.placeCommentDao().deleteAllCommentsFromPlace(data.placeId)

        // Then
        val loadedCount = database.placeCommentDao().getPlaceComments(data.placeId)
        MatcherAssert.assertThat(loadedCount.isEmpty(), `is`(true))
    }

    @Test
    fun updatePlaceCommentsAndGet() = runBlocking {
        // Given
        val placeId = UUID.randomUUID().toString()
        val data = TestUtils.getRandomPlaceCommentDB(placeId)
        val data2 = TestUtils.getRandomPlaceCommentDB(placeId)
        val data3 = TestUtils.getRandomPlaceCommentDB(placeId)
        database.placeCommentDao().savePlaceComments(listOf(data, data2, data3))

        // When
        val loaded = database.placeCommentDao().updatePlaceComments(placeId, listOf(data))

        // Then
        MatcherAssert.assertThat(loaded.size, `is`(1))
        MatcherAssert.assertThat(loaded.first() == data, `is`(true))
    }

}