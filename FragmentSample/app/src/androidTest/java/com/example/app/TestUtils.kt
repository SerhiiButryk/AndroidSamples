/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app

import android.util.Log

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
}