package com.santog.wizards.ui.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.santog.wizards.MainActivity
import com.santog.wizards.R
import com.santog.wizards.utils.EspressoCountingIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class HomeScreenTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoCountingIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun isActivityMainShown() {
        Espresso.onView(withId(R.id.main_activity)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun areHomeButtonsDisplayedOnAppLaunch() {
        Espresso.onView(withId(R.id.bt_hogwarts_staff)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.bt_hogwarts_students)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.bt_search_character)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickStaffButtonAndCheckIfListIsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.bt_hogwarts_staff)).perform(click())
        Espresso.onView(withId(R.id.rv_recycler_view_staff)).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun clickStudentsButtonAndCheckIfListIsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.bt_hogwarts_students)).perform(click())
        Espresso.onView(withId(R.id.rv_recycler_view_students)).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun clickSearchButtonAndCheckIfDialogIsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.bt_search_character)).perform(click())
        Espresso.onView(withId(R.id.cv_character_search)).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoCountingIdlingResource.espressoTestIdlingResource)
    }

}

