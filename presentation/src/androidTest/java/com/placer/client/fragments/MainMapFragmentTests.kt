package com.placer.client.fragments

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.placer.client.*
import com.placer.client.screens.main.MainMapFragment
import com.placer.client.servicelocator.ServiceLocator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class MainMapFragmentTests {

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        ServiceLocator.setDependencyInstance(DependencyInstanceFake(getApplicationContext()))
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun cleanUp() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun mainMapViewsShown() {
        // Given
        val scenario = launchFragmentInContainer<MainMapFragment>(null, R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)

        // Then
        onView(withId(R.id.mapView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.drawerButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.addButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.mainField)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}