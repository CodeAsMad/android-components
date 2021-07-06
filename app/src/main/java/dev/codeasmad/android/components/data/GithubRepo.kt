package dev.codeasmad.android.components.data

import dev.codeasmad.android.networking.Resource
import dev.codeasmad.android.networking.RetrofitProvider

object GithubRepo {
    private val retrofit =
        RetrofitProvider.provide("https://api.github.com/", GithubAuthInterceptor())
    private val githubService = retrofit.create(GithubService::class.java)

    suspend fun searchRepo(query: String): Resource<SearchResponse> {
        return githubService.searchRepo(query)
    }
}