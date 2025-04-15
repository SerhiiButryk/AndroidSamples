package com.example.flowsampleapp

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import log
import org.junit.Test

class CoroutineTest {

    private val TAG = "CoroutineTest"

    @Test
    fun test01() = runTest {
        log("$TAG: test01: started")

        launch { log("$TAG: test01: coroutine 1 started") }
        launch { log("$TAG: test01: coroutine 2 started") }

        // Above coroutines run after this test finishes but before 'runTest' returns

        log("$TAG: test01: finished")
    }

    @Test
    fun test02() = runTest {
        log("$TAG: test01: started")

        val job1 = launch { log("$TAG: test01: coroutine 1 started") }
        val job2 = launch { log("$TAG: test01: coroutine 2 started") }

        // Now coroutines run before this test finishes

        job1.join()
        job2.join()

        // Or as alternative we can ask Test dispatcher to run above coroutines
        // or in other words to run all pending tasks
//        advanceUntilIdle()

        log("$TAG: test01: finished")
    }

}