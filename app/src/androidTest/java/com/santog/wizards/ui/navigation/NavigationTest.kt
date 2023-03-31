package com.santog.wizards.ui.navigation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.santog.wizards.MainActivity
import com.santog.wizards.R
import com.santog.wizards.utils.EspressoCountingIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest {

    private lateinit var activityScenario : ActivityScenario<MainActivity>
    companion object {
        var FIRST_ITEM : Int = 0
        var ITEM_18_BELOW_THE_FOLD : Int = 18
        var ITEM_24_BELOW_THE_FOLD : Int = 24
    }

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoCountingIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun navigateToStaffCharacterDetailScreenWhenItemIsBeingClickedAndGoBack(){
        Espresso.onView(withId(R.id.bt_hogwarts_staff)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_recycler_view_staff))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                FIRST_ITEM,
                    ViewActions.click()
                )
            )
        Espresso.onView(withId(R.id.sv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.cv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.cl_detail_item_inner))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_name))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.iv_detail_image))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.pressBack()
        Espresso.onView(withId(R.id.cl_fragment_staff))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun navigateInAndOutStaffDetailScreen(){
        Espresso.onView(withId(R.id.bt_hogwarts_staff)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_recycler_view_staff))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                ITEM_24_BELOW_THE_FOLD,
                ViewActions.click()
            )
            )
        Espresso.onView(withId(R.id.sv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.cv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.cl_detail_item_inner))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_name))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.iv_detail_image))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.pressBack()
        Espresso.onView(withId(R.id.cl_fragment_staff))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.rv_recycler_view_staff))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                ITEM_18_BELOW_THE_FOLD,
                ViewActions.click()
            )
            )
        Espresso.onView(withId(R.id.sv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.cv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.cl_detail_item_inner))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_name))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.iv_detail_image))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.pressBack()
        Espresso.onView(withId(R.id.cl_fragment_staff))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoCountingIdlingResource.espressoTestIdlingResource)
    }

}
