/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

/**
 * Helper class for automation testing
 */
object TestUtils {

    private val TAG = TestUtils::class.java.simpleName

    /**
     * Helper function to wait for specified time
     */
    fun waitFor(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            Log.i(TAG, "waitFor() Failed to wait for $millis millis, error: $e")
            e.printStackTrace()
        }
    }

    /**
     * Checks if View is present on a screen
     *
     * return false if view is NOT displayed, otherwise true
     */
    // TODO: Investigate if there is better way to do this check
    fun isViewDisplayed(id: Int): Boolean {
        var isDisplayed = true
        try {
            Espresso.onView(ViewMatchers.withId(id))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: Throwable) {
            Log.w(TAG, "Got exception: $e")
            e.printStackTrace()
            isDisplayed = false
        }
        return isDisplayed
    }

}