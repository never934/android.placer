package com.placer.client.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.placer.client.AppClass
import com.placer.client.DataBindingIdlingResource
import com.placer.client.EspressoIdlingResource
import com.placer.client.screens.main.MainMapFragment
import com.placer.client.screens.main.MainMapViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class MainMapFragmentTests {

    private lateinit var repository: ReminderDataSource
    private lateinit var app: AppClass

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun cleanUp() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun remindersEmpty_noData() {
        // Given
        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        

        // Then
        Espresso.onView(withId(R.id.noDataTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}