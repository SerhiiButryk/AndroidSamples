package com.example.flowsampleapp

import app.cash.turbine.test
import com.example.flowsampleapp.viewmodel.SampleViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import log
import org.junit.Test
import kotlin.random.Random

class SampleViewModelTest {

    private val TAG = "SampleViewModelTest"

    // Collect flow and check
    @Test
    fun test01_flow() = runTest {
        log("$TAG: test01_flow: started")

        // Setup test dispatcher and view model
        val dispatcher = StandardTestDispatcher(testScheduler)
        val sampleViewModel = SampleViewModel(object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        })

        var actualValue = 5

        sampleViewModel.countDownFlow.collect { it ->
            log("$TAG: test01_flow: $it")
            assertThat(it).isEqualTo(actualValue)
            actualValue--
        }

        log("$TAG: test01_flow: finished")
    }

    // Collect flow and consume unconsumed events
    @Test
    fun test02_flow() = runTest {
        log("$TAG: test02_flow: started")

        flowOf("one", "two").test {
            // Check that we receive a value !
            assertThat(awaitItem()).isEqualTo("one")
            // Good ! Ignore any other flow value as we don't want them.
            cancelAndIgnoreRemainingEvents()
        }

        log("$TAG: test02_flow: finished")
    }

    // Collect flow and check using Turbine lib
    @Test
    fun test03_flow() = runTest {
        log("$TAG: test03_flow: started")

        // Setup test dispatcher and view model
        val dispatcher = StandardTestDispatcher(testScheduler)
        val sampleViewModel = SampleViewModel(object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        })

        sampleViewModel.countDownFlow.test {
            for (i in 5 downTo 1) {
                val nextValue = awaitItem()
                log("$TAG: test03_flow: $nextValue")
                assertThat(nextValue).isEqualTo(i)
            }
        }

        // We don't need something like consume or cancel remaining events here
        // because after test block is finished, the flow gets canceled and we stop
        // emitting new value in flow
        //
        // If we receive an exception at the end of test block, then this could mean the code has changed
        // and we might need to update the test which is a good hint

        log("$TAG: test03_flow: finished")
    }

    // Collect flow and check using Turbine lib + StandardTestDispatcher
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test04_flow() = runTest {
        log("$TAG: test04_flow: started")

        // Setup test dispatcher and view model
        val dispatcher = StandardTestDispatcher(testScheduler)
        val sampleViewModel = SampleViewModel(object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        })

        sampleViewModel.countDownFlow.test {
            for (i in 5 downTo 1) {
                val nextValue = awaitItem()
                log("$TAG: test04_flow: $nextValue")
                assertThat(nextValue).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }

        // We don't need something like consume or cancel remaining events here
        // because after test block is finished, the flow gets canceled and we stop
        // emitting new value in flow
        //
        // If we receive an exception at the end of test block, then this could mean the code has changed
        // and we might need to update the test which is a good hint

        log("$TAG: test04_flow: finished")
    }

    // Terminal operators
    @Test
    fun test05_flow() = runTest {
        log("$TAG: test05_flow: started")

        val flow = flowOf(1, 2, 3, 4, 5)

        val res = flow.count()
        assertThat(res).isEqualTo(5)

        log("$TAG: test05_flow: finished")
    }

    // Terminal operators
    @Test
    fun test06_flow() = runTest {
        log("$TAG: test06_flow: started")

        val flow = flowOf(1, 2, 3, 4, 5)

        // toList and toSet are terminal operators
        val list = flow.toList()
        val set = flow.toSet()

        log("$TAG: test06_flow: list = $list")
        log("$TAG: test06_flow: set = $set")

        log("$TAG: test06_flow: finished")
    }

    // Terminal operators
    @Test
    fun test07_flow() = runTest {
        log("$TAG: test07_flow: started")

        val flow = flowOf(1, 2, 3, 4, 5)

        // Calculate a sum of all values
        val res = flow.reduce { acc, value ->
            acc + value
        }

        assertThat(res).isEqualTo(15)

        // The same bu initial value is 10
        val res2 = flow.fold(10) { acc, value ->
            acc + value
        }

        assertThat(res2).isEqualTo(25)

        log("$TAG: test07_flow: finished")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test08_hot_flow() = runTest {
        log("$TAG: test08_hot_flow: started")

        val flow = flow {
            log("$TAG: Flow started")

            connect()

            try {
                (0..10).forEach { i ->
                    log("$TAG: Flow sends new value")
                    emit(Random.nextInt(10))
                    delay(100)
                }
            } finally {
                log("$TAG: Flow finally block is called !")
                disconnect()
            }

            log("$TAG: Flow finished")
        }

// OR
//        val sharedFlow = flow.shareIn(scope = this, started = SharingStarted.Eagerly, replay = 0)

        // Here we should start flow on background test scope if we want to cancel it when we want.
        // Otherwise the test scope gonna be canceled which is an error.
        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val value = flow.stateIn(this)
                .collect {
                    if (it == 9) {
                        log("$TAG: test08_hot_flow: got value = $it, done !!! cancel this flow and stop the test !!!")
                        cancel()
                    }
                }
            log("$TAG: test08_hot_flow: val = $value")
        }

        // Wait completion
        job.join()

        log("$TAG: test08_hot_flow: finished")
    }

    private suspend fun disconnect() {
        delay(1000)
    }

    private suspend fun connect() {
        delay(1000)
    }

}