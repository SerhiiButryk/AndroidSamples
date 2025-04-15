package com.example.myapplication.network

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.Base64

private const val TAG = "CoroutineSample"

val username = "SerhiiButryk"
// https://github.com/settings/tokens/new
val password = "<insert your token>"

fun newHtpClient(): GitHubService {
    return createGitHubService(username, password)
}

fun createGitHubService(username: String, password: String): GitHubService {
    return getRetrofit(username, password).create(GitHubService::class.java)
}

fun newHtpClient2(): GitHubService2 {
    return createGitHubService2(username, password)
}

fun createGitHubService2(username: String, password: String): GitHubService2 {
    return getRetrofit(username, password).create(GitHubService2::class.java)
}

private fun getRetrofit(username: String, password: String): Retrofit {
    val authToken = "Basic " + Base64.getEncoder().encode("$username:$password".toByteArray()).toString(Charsets.UTF_8)
    val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .header("Authorization", authToken)
            val request = builder.build()
            chain.proceed(request)
        }
        .build()

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build()
    return retrofit
}

@OptIn(ExperimentalSerializationApi::class)
fun createHttpClient(username: String, password: String, baseUrl: String, logging: Boolean): GitHubService {

    val authToken = "Basic " + Base64.getEncoder().encode("$username:$password".toByteArray())
        .toString(Charsets.UTF_8)

    val httpClient = OkHttpClient.Builder()
        // Add application interceptor to add additional headers, log response & request pair
        // We also can add addNetworkInterceptor() for intercepting requests from/to network
        .addInterceptor { chain ->

            val original = chain.request().apply {
                if (logging) {
                    val logHeaders = headers.run { if (size == 0) "EMPTY" else this }
                    Log.i(
                        TAG, "onIntercept() >>>> ${method} ${url}" +
                                "\n---- HEADERS ----\n${logHeaders}\n---- END HEADERS ----"
                    )
                }
            }

            val builder = original.newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .header("Authorization", authToken)

            val request = builder.build()

            // This is a blocking call. We are going to execute the provided request and receive a response
            chain.proceed(request).apply {
                if (logging) {
                    Log.i(
                        TAG, "onIntercept() <<<< ${code} ${this.request.url}" +
                                "\n---- HEADERS ----\n" +
                                "${headers}" +
                                "---- END HEADERS ----\n"
                    )
                }
            }
        }
        .build()

    val contentType = "application/json".toMediaType()

    // A json converter
    val json = Json { ignoreUnknownKeys = true }

    // Configure retrofit with our custom http client
    // and converters to transform the received responses
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)
        .build()

    return retrofit.create(GitHubService::class.java)
}

// An example of logging interceptor
private object LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().apply {
            val logHeaders = headers.run { if (size == 0) "EMPTY" else this }
            Log.i(
                TAG, "onIntercept() ${method} >>>> ${url}" +
                        "\n---- HEADERS ----\n${logHeaders}---- END HEADERS ----"
            )
        }

        val response = chain.proceed(request).apply {
            Log.i(
                TAG, "onIntercept() ${code} <<<< ${request.url}" +
                        "\n---- HEADERS ----\n" +
                        "${headers}" +
                        "---- END HEADERS ----\n"
            )
        }

        return response
    }
}