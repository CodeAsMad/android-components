package dev.codeasmad.android.components.data

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("language") val language: String
)