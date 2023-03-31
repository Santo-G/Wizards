package com.santog.wizards.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.santog.wizards.MainActivity
import com.santog.wizards.R
import com.santog.wizards.utils.EspressoCountingIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class StudentsScreenTest {

    private lateinit var activityScenario : ActivityScenario<MainActivity>
    companion object {
        var THIRD_ITEM : Int = 2
        var ITEM_15_BELOW_THE_FOLD : Int = 15
        var ITEM_47_BELOW_THE_FOLD : Int = 47
    }

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoCountingIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun scrollToItemBelowFoldAndCheckIfItemIsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.bt_hogwarts_students)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_recycler_view_students))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    ITEM_15_BELOW_THE_FOLD
                )
            )
        Espresso.onView(hasItemAtPosition(ITEM_15_BELOW_THE_FOLD, withId(R.id.cv_character_item)))
            .check(
                ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed())
            )
    }

    @Test
    fun selectStaffCharacterListItemGoBackAndCheckIfItemIsDisplayed(){
        Espresso.onView(withId(R.id.bt_hogwarts_students)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_recycler_view_students))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    THIRD_ITEM,
                    ViewActions.click()
                )
            )
        Espresso.pressBack()
        Espresso.onView(hasItemAtPosition(THIRD_ITEM, withId(R.id.cv_character_item)))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun isDetailScreenDisplayedWhenItemClicked(){
        Espresso.onView(withId(R.id.bt_hogwarts_students)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_recycler_view_students))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ITEM_15_BELOW_THE_FOLD,
                    ViewActions.click()
                )
            )
        Espresso.onView(withId(R.id.sv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun scrollToLastItemBelowFoldAndCheckIfItemIsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.bt_hogwarts_students)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_recycler_view_students))
            .perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>(), ViewActions.click())
        Espresso.onView(withId(R.id.sv_detail_item))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoCountingIdlingResource.espressoTestIdlingResource)
    }

    // Utilities
    private fun hasItemAtPosition(position: Int, matcher: Matcher<View>) : Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has item at position $position : ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val viewHolder = item?.findViewHolderForAdapterPosition(position)
                return matcher.matches(viewHolder?.itemView)
            }
        }
    }

}
