package dev.codeasmad.android.networking

import java.lang.Exception

sealed class Resource<D : Any> {
    class Success<D : Any>(val data: D) : Resource<D>()
    class Failure<D : Any>(val error: Exception?) : Resource<D>()
}

object NetworkException: Exception()
class ApiException(val code: Int): Exception()