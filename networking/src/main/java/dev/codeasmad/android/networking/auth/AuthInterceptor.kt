package dev.codeasmad.android.networking.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

abstract class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val annotation =
            chain.request().tag(Invocation::class.java)?.method()?.getAnnotation(NoAuth::class.java)

        return chain.proceed(
            if (annotation == null) {
                authenticateRequest(chain.request())
            } else {
                chain.request()
            }
        )
    }

    abstract fun authenticateRequest(request: Request): Request
}