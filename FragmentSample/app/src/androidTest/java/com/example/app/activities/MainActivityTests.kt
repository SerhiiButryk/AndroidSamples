/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.activities

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.app.MainActivity
import com.example.app.R
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Tests for MainActivity
 */
class MainActivityTests {

    private lateinit var activityUnderTheTest: ActivityScenario<MainActivity>

    companion object {
        private val TAG = MainActivityTests::class.simpleName
        private const val TEXT_TO_ENTER = "John"
        private const val EXPECTED_TEXT = "Hello, John !"
    }

    @Before
    fun setup() {
        Log.i(TAG, "setup() Started")

        // Launch the activity
        Log.i(TAG, "setup() 1. Launch the activity")

        activityUnderTheTest = ActivityScenario.launch(MainActivity::class.java, null)

        Log.i(TAG, "setup() Finished")
    }

    @After
    fun tearDown() {
        Log.i(TAG, "tearDown() Started")

        // Close the activity
        Log.i(TAG, "setup() 1. Close the activity")

        activityUnderTheTest.close()

        Log.i(TAG, "tearDown() Finished")
    }

    @Test
    fun test001_open_activity_and_enter_info() {
        Log.i(TAG, "test001_open_activity_and_enter_info() Started")

        // 1. Verify that Greetings UI is shown
        Log.i(TAG, "test001_open_activity_and_enter_info() 1. Verify that Greetings UI is shown")

        Espresso.onView(ViewMatchers.withId(R.id.main_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // 2. Open EnterInfo UI
        Log.i(TAG, "test001_open_activity_and_enter_info() 2. Open EnterInfo UI")

        Espresso.onView(ViewMatchers.withId(R.id.add_info_btn))
                .perform(ViewActions.click())

        // 3. Verify that EnterInfo UI is shown
        Log.i(TAG, "test001_open_activity_and_enter_info() 3. Verify that EnterInfo UI is shown")

        Espresso.onView(ViewMatchers.withId(R.id.title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // 4. Enter user name
        Log.i(TAG, "test001_open_activity_and_enter_info() 4. Enter user name")

        Espresso.onView(ViewMatchers.withId(R.id.input_txt_field))
                .perform(ViewActions.typeText(TEXT_TO_ENTER))

        Espresso.onView(ViewMatchers.withId(R.id.ok_btn))
                .perform(ViewActions.click())

        // 5. Navigate back
        Log.i(TAG, "test001_open_activity_and_enter_info() 5. Navigate back")

        ViewActions.pressBack()

        // 6. Verify greetings text is shown
        Log.i(TAG, "test001_open_activity_and_enter_info() 6. Verify greetings text is shown")

        // Open EnterInfo UI is shown
        Espresso.onView(ViewMatchers.withId(R.id.main_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Greetings text is shown
        Espresso.onView(ViewMatchers.withText(EXPECTED_TEXT))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Log.i(TAG, "test001_open_activity_and_enter_info() Finished")
    }

}