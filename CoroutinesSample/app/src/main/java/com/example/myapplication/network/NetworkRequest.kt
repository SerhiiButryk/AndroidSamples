package com.example.myapplication.network

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Serializable
data class Repo(val id: Long, val name: String)

@Serializable
data class User(val login: String, val contributions: Int)

interface GitHubService {

    @GET("orgs/{org}/repos?per_page=100")
    suspend fun getOrgRepos(
        @Path("org") org: String
    ): Response<List<Repo>>

    @GET("repos/{owner}/{repo}/contributors?per_page=100")
    suspend fun getRepoContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<User>>
}

interface GitHubService2 {

    @GET("orgs/{org}/repos?per_page=100")
    fun getOrgRepos(
        @Path("org") org: String
    ): Call<List<Repo>>

    @GET("repos/{owner}/{repo}/contributors?per_page=100")
    fun getRepoContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>
}