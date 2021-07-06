package dev.codeasmad.android.networking.resource

import dev.codeasmad.android.networking.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResourceCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType = getRawType(returnType)

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // Parameterized return type is required
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<Resource<Foo>> or Call<Resource<out Foo>>"
        }

        // get response type inside `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // Check whether the type is `Resource`
        if (getRawType(responseType) != Resource::class.java) {
            return null
        }

        // Resource should be parameterized
        check(responseType is ParameterizedType) {
            "Resource must be parameterized as Resource<Foo> or Resource<out Foo>"
        }

        val successType = getParameterUpperBound(0, responseType)

        return ResourceCallAdapter<Any>(successType)
    }
}