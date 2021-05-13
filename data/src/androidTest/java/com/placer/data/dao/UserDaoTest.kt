package com.placer.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.placer.data.di.module.room.AppDatabase
import com.placer.data.utils.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

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
    fun updateUsersAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomUserDB(null)
        val data2 = TestUtils.getRandomUserDB(null)
        val data3 = TestUtils.getRandomUserDB(null)
        database.userDao().saveUsers(listOf(data2, data3))

        // When
        val loaded = database.userDao().updateUsers(listOf(data))

        // Then
        MatcherAssert.assertThat(loaded.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loaded.first() == data, CoreMatchers.`is`(true))
    }

    @Test
    fun saveUsersAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomUserDB(null)
        database.userDao().saveUsers(listOf(data))

        // When
        val loaded = database.userDao().getUsers()

        // Then
        MatcherAssert.assertThat(loaded.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loaded.first() == data, CoreMatchers.`is`(true))
    }

    @Test
    fun saveUserAndGet() = runBlocking {
        // Given
        val data = TestUtils.getRandomUserDB(null)
        database.userDao().saveUser(data)

        // When
        val loaded = database.userDao().getUser(data.id)

        // Then
        MatcherAssert.assertThat(loaded == data, CoreMatchers.`is`(true))
    }

    @Test
    fun saveUsersAndDeleteAll() = runBlocking {
        // Given
        val data = TestUtils.getRandomUserDB(null)
        database.userDao().saveUser(data)

        // When
        database.userDao().deleteUsers()
        val loaded = database.userDao().getUsers()

        // Then
        MatcherAssert.assertThat(loaded.isEmpty(), CoreMatchers.`is`(true))
    }

}