/*
 *  Copyright 2022. Happy coding ! :)
 *  Author: Serhii Butryk
 */

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*

fun <T: Any> log(message: T) {
    println("INFO: [${SimpleDateFormat("hh:mm:ss").format(Date())} " +
            "TID:${Thread.currentThread().id} TNAME:${Thread.currentThread().name}] $message")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> CoroutineScope.logDebug(message: T) {
    log("DEBUG: [coroutine: ${coroutineContext[CoroutineName]?.name} = $this, " +
            "my parent: ${coroutineContext[Job]?.parent},] $message")
}
