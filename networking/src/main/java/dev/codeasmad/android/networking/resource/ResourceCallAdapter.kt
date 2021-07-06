package dev.codeasmad.android.networking.resource

import dev.codeasmad.android.networking.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResourceCallAdapter<Data : Any>(
    private val successType: Type,
) : CallAdapter<Data, Call<Resource<Data>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<Data>): Call<Resource<Data>> {
        return ResourceCall(call)
    }
}