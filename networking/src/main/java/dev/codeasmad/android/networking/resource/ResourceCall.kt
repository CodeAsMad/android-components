package dev.codeasmad.android.networking.resource

import dev.codeasmad.android.networking.ApiException
import dev.codeasmad.android.networking.NetworkException
import dev.codeasmad.android.networking.Resource
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ResourceCall<S : Any>(
    private val delegate: Call<S>,
) : Call<Resource<S>> {
    override fun enqueue(callback: Callback<Resource<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                processResponse(response, callback)
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> NetworkException
                    else -> null
                }

                callback.onResponse(
                    this@ResourceCall,
                    Response.success(Resource.Failure<S>(networkResponse))
                )
            }

        })
    }

    private fun processResponse(
        response: Response<S>,
        callback: Callback<Resource<S>>
    ) {
        val body = response.body()
        val code = response.code()
        val error = response.errorBody()

        if (response.isSuccessful) {
            if (body != null) {
                callback.onResponse(
                    this@ResourceCall,
                    Response.success(Resource.Success(body))
                )
            } else {
                // Response is success, but body is empty @todo
                callback.onResponse(
                    this@ResourceCall,
                    Response.success(Resource.Failure(null))
                )
            }
        } else {
            val errorBody: ResponseBody? = when {
                error?.contentLength() == 0L -> null
                else -> error
            }

            if (errorBody != null) {
                callback.onResponse(this, Response.success(Resource.Failure(ApiException(code))))
            } else {
                callback.onResponse(this, Response.success(Resource.Failure(NetworkException)))
            }
        }
    }

    override fun clone() = ResourceCall<S>(delegate.clone())

    override fun execute(): Response<Resource<S>> {
        throw UnsupportedOperationException("ResourceCall doesn't support execute")

    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}