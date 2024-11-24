package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.TAG
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

var count: Int = 1

class NetworkTaskManager {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var parentJob: Job? = null

    fun startRequest() {
        parentJob?.cancel()

        parentJob = scope.launch(CoroutineName("Coroutine#" + count++)) {

            val name = coroutineContext[CoroutineName]?.name
            val job = coroutineContext[Job]
            val thread = Thread.currentThread().name

            Log.i(TAG, "startTask() {$name, $job} started on $thread")

            try {
                makeRequestAsyncAndWait()
            } catch (e: CancellationException) {
                Log.i(TAG, "startTask() {$name, $job} was canceled on $thread")
            }

            Log.i(TAG, "startTask() {$name, $job} has finished on $thread")
        }
    }

    fun cancelRequest() {
        parentJob?.cancel()
    }

    fun cancelAll() {
        scope.cancel()
    }

    fun startTestRequest() {
        thread {
            Log.i(TAG, "startTestRequest() IN")
            val future = makeRequestAsyncNotWait(scope)
            val result = future.join()
            Log.i(TAG, "startTestRequest() done = ${result.size}")
        }
    }
}

/**
 * Using Completable future approach
 */
private fun makeRequestAsyncNotWait(scope: CoroutineScope): CompletableFuture<List<Repo>> {

    return scope.future(CoroutineName("Coroutine#" + count++)) {

        val httpClient = newHtpClient()

        val orgName = "kotlin"

        val results = httpClient.getOrgRepos(orgName).apply {
            // Log response
            Log.i(TAG, "makeRequestAsyncAndWait() Got repos = ${bodyAsList()}")
        }

        // Returned result
        results.bodyAsList()
    }
}

/**
 * Using Continuation callback approach
 */
private suspend fun makeRequestAsyncAndWait() {

    val httpClient = newHtpClient2()

    val orgName = "kotlin"

    httpClient.getOrgRepos(orgName).await().apply {
        // Log response
        Log.i(TAG, "makeRequestAsyncAndWait() Got repos = ${this.size}")
    }
}

private suspend fun <T> Call<T>.await(): List<Repo> = suspendCoroutine { callback ->
    Log.i(TAG, "await() IN")
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, res: Response<T>) {
            Log.i(TAG, "onResponse()")
            if (res.isSuccessful) {
                callback.resumeWith(Result.success(res.body()!! as List<Repo>))
            } else {
                callback.resumeWithException(IllegalStateException("No response"))
            }
        }

        override fun onFailure(call: Call<T>, e: Throwable) {
            Log.i(TAG, "onFailure()")
            callback.resumeWithException(e)
        }
    })
    Log.i(TAG, "await() OUT")
}

/**
* Approach with channels
*/
private suspend fun makeRequestUseChannels() = coroutineScope {

    val httpClient = newHtpClient()

    val orgName = "kotlin"

    val repos: List<Repo> = httpClient
        .getOrgRepos(orgName)
        .apply {
            // Log response
            Log.i(TAG, "makeRequestUseChannels() Received repositories = ${bodyAsList().size}")
        }
        .bodyAsList()

    val channel = Channel<List<User>>()

    repos.forEach { repo ->
        launch {
            Log.i(TAG, "makeRequestUseChannels() Started getting contributors ${coroutineContext[CoroutineName]?.name} for ${repo.name}")

            val users = httpClient
                .getRepoContributors(orgName, repo.name)
                .apply {
                    // Log response
                    Log.i(TAG, "makeRequestUseChannels() Received contributors ${bodyAsList().size} for ${repo.name}")
                }
                .bodyAsList()

            Log.i(TAG, "makeRequestUseChannels() Sending result...")

            // Send the result
            channel.send(users)

            Log.i(TAG, "makeRequestUseChannels() Sent !!!")
        }
    }

    val allResults = mutableListOf<User>()

    repeat(repos.size) {
        Log.i(TAG, "makeRequestUseChannels() Checking if we have a result")
        // It gets suspended or continued when new data in available in the channel
        val result = channel.receive()
        allResults.addAll(result)
        // Update UI or notify that we got some data
        // Do not do it now just log a message
        Log.i(TAG, "makeRequestUseChannels() Received results")
    }
}

/**
 * Concurrent approach
 */
private suspend fun makeRequestConcurrent() = coroutineScope {

    val httpClient = newHtpClient()

    val orgName = "kotlin"

    val repos: List<Repo> = httpClient
        .getOrgRepos(orgName)
        .apply {
            // Log response
            Log.i(TAG, "makeRequestConcurrent() Received repositories = ${bodyAsList().size}")
        }
        .bodyAsList()

    val deferredResult: List<Deferred<List<User>>> = repos.map { repo ->

        async(CoroutineName("Coroutine#" + count++)) {

            // We can cancel the coroutine if we want
//            if (someCondition) {
//                Log.i(TAG, "makeRequestConcurrent() cancelling")
//                cancel()
//            }

            Log.i(TAG, "makeRequestConcurrent() Started ${coroutineContext[CoroutineName]?.name} for ${repo.name}")

            httpClient
                .getRepoContributors(orgName, repo.name)
                .apply {
                    // Log response
                    Log.i(TAG, "makeRequestConcurrent() Received contributors ${bodyAsList().size} for ${repo.name}")
                }
                .bodyAsList()
        }

    }

    val listUsers: List<User> = deferredResult.awaitAll().flatten()

    Log.i(TAG, "makeRequestConcurrent() Done result = ${listUsers.formatUserContribution()}")
}

/**
 * Synchronous approach
 */
private suspend fun makeRequest() {

    val httpClient = newHtpClient()

    val orgName = "kotlin"

    val repos: List<Repo> = httpClient
        .getOrgRepos(orgName) // Executing the network request
        .apply {
            // Log response
            Log.i(TAG, "makeRequest() Received repositories = ${bodyAsList().size}")
        }
        .bodyAsList()

    val listUsers = repos.flatMap { repo ->
        httpClient
            .getRepoContributors(orgName, repo.name) // Executing the network request
            .apply {
                // Log response
                Log.i(TAG, "makeRequest() Received contributors ${bodyAsList().size} for ${repo.name}")
            }
            .bodyAsList()
    }

    Log.i(TAG, "makeRequest() Done result = ${listUsers.formatUserContribution()}")
}

fun <T> Response<List<T>>.bodyAsList() = body() ?: emptyList()

fun List<User>.formatUserContribution(): List<User> {
    return groupBy { it.login }
        .map { (key: String, value: List<User>) ->
            User(key, value.sumOf { it.contributions })
        }
        .sortedByDescending { it.contributions }
}