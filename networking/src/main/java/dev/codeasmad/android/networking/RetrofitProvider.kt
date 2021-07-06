package dev.codeasmad.android.networking

import com.google.gson.Gson
import dev.codeasmad.android.networking.auth.AuthInterceptor
import dev.codeasmad.android.networking.resource.ResourceCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    private val gson = Gson()
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .callTimeout(6L, TimeUnit.SECONDS)
        .connectTimeout(6L, TimeUnit.SECONDS)
        .readTimeout(6L, TimeUnit.SECONDS)
        .writeTimeout(6L, TimeUnit.SECONDS)
        .build()

    fun provide(
        baseUrl: String,
        authInterceptor: AuthInterceptor? = null
    ): Retrofit {
        val client = if (authInterceptor != null) {
            httpClient.newBuilder().addInterceptor(authInterceptor).build()
        } else {
            httpClient
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(ResourceCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}