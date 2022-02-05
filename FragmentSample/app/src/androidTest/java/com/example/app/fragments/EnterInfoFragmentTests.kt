/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.fragments

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
import com.example.app.ui.EnterInfoFragment
import com.example.app.ui.factory.AppFragmentFactory
import com.example.app.viewmodel.AppViewModel
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Tests for EnterInfoFragment
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class EnterInfoFragmentTests {

    // Launches fragment and gets ViewModel instance
    @Before
    fun setup() {
        Log.i(TAG, "setup() Started")

        // 1. Create fragment factory
        Log.i(TAG, "setup() 1. Create fragment factory")

        val appFragmentFactory: FragmentFactory = AppFragmentFactory(UserInfo())

        // 2. Launch fragment
        Log.i(TAG, "setup() 2. Launch fragment")

        Assert.assertNull("Fragment is NOT null", fragmentUnderTheTest)

        fragmentUnderTheTest = FragmentScenario.launchInContainer(EnterInfoFragment::class.java,
            null, R.style.AppTheme_NoActionBar, appFragmentFactory)

        // 3. Get ViewModel instance
        fragmentUnderTheTest?.onFragment { fragment ->
            try {
                val viewModelField = fragment.javaClass.getDeclaredField("appViewModel")
                viewModelField.isAccessible = true
                viewModel = viewModelField.get(fragment) as AppViewModel
            } catch (e: Exception) {
                Log.e(TAG, "setup() Failed to get ViewModel instance: $e" )
                e.printStackTrace()
            }
        }

        Assert.assertNotNull("ViewModel is null", viewModel)

        Log.i(TAG, "setup() Finished")
    }

    // Closes fragment and clears up data
    @After
    fun tearDown() {
        Log.i(TAG, "tearDown() Started")

        // 5. Close fragment
        Log.i(TAG, "tearDown() 1. Close fragment")

        Assert.assertNotNull("Fragment is null", fragmentUnderTheTest)

        fragmentUnderTheTest?.close()
        fragmentUnderTheTest = null
        viewModel = null

        Log.i(TAG, "tearDown() Finished")
    }

    /**
     * Test for launching fragment and testing UI elements
     * Steps:
     * 1. Test checks title text
     * 2. Test verifies ViewModel data
     * 3. Test enters name and clicks button
     * 4. Test verifies ViewModel data
     */

    // TODO: Need to verify if a toast message is shown. I didn't find any working solution.
    // The next code fails on emulator with Android API 30. Need to find a resolution for this issue.
    // Similar GitHub issue: https://github.com/android/android-test/issues/803
    // Espresso.onView(ViewMatchers.withText(appContext.getString(R.string.enter_name_info)))
    // .inRoot(ToastMatcher())
    // .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    @Test
    fun test01_launch_fragment_and_enter_name() {
        Log.i(TAG, "test01_launch_fragment_and_enter_name() Started")

        // 1. Check title text
        Log.i(TAG, "test01_launch_fragment_and_enter_name() 1. Check title text")

        val expectedText = appContext.getString(R.string.title_enter_info_fragment)
        Espresso.onView(ViewMatchers.withId(R.id.title))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))

        // Check that ViewModel has correct value before button click
        var valueToCheck = viewModel?.userInfoProvidedLiveData?.value
        Assert.assertEquals("Action finish flag has wrong value, expected: false, got: $valueToCheck", valueToCheck, false)

        // 2. Enter text
        Log.i(TAG, "test01_launch_fragment_and_enter_name() 2. Enter text")

        Espresso.onView(ViewMatchers.withId(R.id.input_txt_field))
            .perform(ViewActions.typeText(TEXT_TO_ENTER))

        // 3. Perform button click
        Log.i(TAG, "test01_launch_fragment_and_enter_name() 3. Perform button click")

        Espresso.onView(ViewMatchers.withId(R.id.ok_btn))
            .perform(ViewActions.click())

        // Check that ViewModel has correct value after button click
        valueToCheck = viewModel?.userInfoProvidedLiveData?.value
        Assert.assertEquals("Action finish flag has wrong value, expected: true, got: $valueToCheck", valueToCheck, true)

        Log.i(TAG, "test01_launch_fragment_and_enter_name() Finished")
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

        Espresso.onView(ViewMatchers.withId(R.id.title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Log.i(TAG, "test02_recreate_fragment() Finished")
    }

    /**
     * Test for testing back button navigation
     * Steps:
     * 1. Test clicks back button
     * 2. Test checks checks if confirm dialog is shown
     * 3. Test clicks 'ok' button
     * 4. Test verifies that fragment is closed
     */
    @Test
    fun test03_back_button_click_and_confirm_dialog() {
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Started")

        // 1. Click back button
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Click back button")

        Espresso.pressBack()

        // 2. Check checks if confirm dialog is shown
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Check checks if confirm dialog is shown")

        val expectedText = appContext.getString(R.string.enter_info_fragment_dialog_confirm_message)
        Espresso.onView(ViewMatchers.withText(expectedText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // 3. Click 'ok' button
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Click 'ok' button")

        Espresso.onView(ViewMatchers.withText(android.R.string.ok))
            .perform(ViewActions.click())

        // 4. Verify that fragment is closed
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Verify that fragment is closed")

        Assert.assertEquals("Fragment is not closed", TestUtils.isViewDisplayed(R.id.title), false)

        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Finished")
    }

    /**
     * Test for testing back button navigation
     * Steps:
     * 1. Test clicks back button
     * 2. Test checks checks if confirm dialog is shown
     * 3. Test clicks 'cancel' button
     * 4. Test verifies that fragment is NOT closed
     */
    @Test
    fun test04_back_button_click_and_cancel_dialog() {
        Log.i(TAG, "test03_back_button_click_and_cancel_dialog() Started")

        // 1. Click back button
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Click back button")

        Espresso.pressBack()

        // 2. Check checks if confirm dialog is shown
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Check checks if confirm dialog is shown")

        var expectedText = appContext.getString(R.string.enter_info_fragment_dialog_confirm_message)
        Espresso.onView(ViewMatchers.withText(expectedText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // 3. Click 'cancel' button
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Click 'cancel' button")

        Espresso.onView(ViewMatchers.withText(android.R.string.cancel))
            .perform(ViewActions.click())

        // 4. Verify that fragment is NOT closed
        Log.i(TAG, "test03_back_button_click_and_confirm_dialog() Verify that fragment is NOT closed")

        Assert.assertEquals("Fragment is not closed", TestUtils.isViewDisplayed(R.id.title), true)

        Log.i(TAG, "test03_back_button_click_and_cancel_dialog() Finished")
    }

    companion object {
        private val TAG = EnterInfoFragmentTests::class.simpleName
        private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        private const val TEXT_TO_ENTER = "John"
        private var viewModel: AppViewModel? = null

        private var fragmentUnderTheTest:FragmentScenario<EnterInfoFragment>? = null
    }

}