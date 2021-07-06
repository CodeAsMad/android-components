package dev.codeasmad.android.components.data

import android.util.Log
import dev.codeasmad.android.networking.auth.AuthInterceptor
import okhttp3.Request

class GithubAuthInterceptor : AuthInterceptor() {
    override fun authenticateRequest(request: Request): Request {
        Log.i("Authentication", "${request.method} - ${request.url}")
        return request.newBuilder()
            .addHeader("Token", "SomeValue")
            .build()
    }
}