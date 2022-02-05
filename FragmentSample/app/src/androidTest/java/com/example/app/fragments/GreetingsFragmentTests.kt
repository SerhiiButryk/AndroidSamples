/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.app.R
import com.example.app.TestUtils
import com.example.app.model.UserInfo
import com.example.app.ui.GreetingsFragment
import com.example.app.ui.factory.AppFragmentFactory
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Tests for GreetingsFragment
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class GreetingsFragmentTests {

    @Before
    fun setup() {
        Log.i(TAG, "setup() Started")

        // 1. Create fragment factory
        Log.i(TAG, "setup() 1. Create fragment factory")

        val appFragmentFactory: FragmentFactory = AppFragmentFactory(UserInfo())

        // 2. Prepare arguments for fragment
        Log.i(TAG, "setup() 2. Prepare arguments for fragment")

        val arguments = Bundle()
        arguments.putString(GreetingsFragment.TITLE_NAME_ARG, appContext.getString(R.string.greetings_title))

        // 3. Launch fragment
        Log.i(TAG, "setup() 3. Launch fragment")

        fragmentUnderTheTest = FragmentScenario.launchInContainer(GreetingsFragment::class.java,
            arguments, R.style.AppTheme_NoActionBar, appFragmentFactory)

        Log.i(TAG, "setup() Finished")
    }

    @After
    fun tearDown() {
        Log.i(TAG, "tearDown() Started")

        // 5. Close fragment
        Log.i(TAG, "test02_recreate_fragment() 1. Close fragment")

        fragmentUnderTheTest?.close()
        fragmentUnderTheTest = null

        Log.i(TAG, "tearDown() Finished")
    }

    /**
     * Test for launching fragment and testing UI elements
     * Steps:
     * 1. Test checks fragment title text
     * 2. Test performs button click
     */
    @Test
    fun test01_launch_fragment_and_check_views() {
        Log.i(TAG, "test01_launch_fragment_and_check_views() Started")

        // 1. Check fragment title text
        Log.i(TAG, "test01_launch_fragment_and_check_views() 1. Check fragment title text")

        val expectedTitleText = appContext.getString(R.string.greetings_title)
        Espresso.onView(ViewMatchers.withId(R.id.main_title))
                .check(ViewAssertions.matches(ViewMatchers.withText(expectedTitleText)))

        // 2. Perform button click
        Log.i(TAG, "test01_launch_fragment_and_check_views() 2. Perform button click")
        Espresso.onView(ViewMatchers.withId(R.id.add_info_btn))
                .perform(ViewActions.click())

        Log.i(TAG, "test01_launch_fragment_and_check_views() Finished")
    }

    /**
     * Test for testing recreation of fragment
     * Steps:
     * 1. Test recreates fragment
     * 2. Test checks that fragment is shown
     */
    @Test
    fun test02_recreate_fragment() {
        Log.i(TAG, "test02_recreate_fragment() Started")

        // 1. Recreate fragment
        Log.i(TAG, "test02_recreate_fragment() 1. Recreate fragment")

        fragmentUnderTheTest?.recreate()

        // 2. Check that fragment is shown
        Log.i(TAG, "test02_recreate_fragment() 2. Check that fragment is shown")

        Assert.assertEquals("Fragment is not shown", TestUtils.isViewDisplayed(R.id.main_title), true)

        Log.i(TAG, "test02_recreate_fragment() Finished")

    }

    companion object {
        private val TAG = GreetingsFragmentTests::class.java.simpleName
        private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        private var fragmentUnderTheTest:FragmentScenario<GreetingsFragment>? = null
    }

}