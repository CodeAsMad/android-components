package dev.codeasmad.android.components.data

import dev.codeasmad.android.networking.auth.NoAuth
import dev.codeasmad.android.networking.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @NoAuth
    @GET("/search/repositories")
    suspend fun searchRepo(@Query("q") query: String): Resource<SearchResponse>
}