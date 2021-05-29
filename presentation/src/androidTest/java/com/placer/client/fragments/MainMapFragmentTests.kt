package com.placer.client.fragments

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.placer.client.*
import com.placer.client.screens.main.MainMapFragment
import com.placer.client.servicelocator.ServiceLocator
import com.placer.data.repository.fake.FakePlaceRepository
import com.placer.domain.TestUtils
import com.placer.domain.entity.place.Place
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class MainMapFragmentTests {

    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private lateinit var places: ArrayList<Place>

    @Before
    fun init() {
        places = arrayListOf(
            TestUtils.getRandomPlace(), TestUtils.getRandomPlace(), TestUtils.getRandomPlace()
        )
        val repository = FakePlaceRepository(places)
        ServiceLocator.setDependencyInstance(
            DependencyInstanceFake(
                getApplicationContext(),
                placeRepository = repository
            )
        )
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

    @Test
    fun inputTextPlacesInMainFieldShown() {
        // Given
        val repoPlaces = places
        val scenario = launchFragmentInContainer<MainMapFragment>(null, R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)

        // Then
        onView(withId(R.id.editField)).perform(ViewActions.typeText(repoPlaces[0].name))

        // When
        onView(withId(R.id.placesContainer)).check(ViewAssertions.matches(ViewMatchers.hasChildCount(1)))
    }
}