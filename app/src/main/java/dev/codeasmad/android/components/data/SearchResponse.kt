package dev.codeasmad.android.components.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val inCompleteResults: Boolean,
    @SerializedName("items") val repos: List<Repository>
)