package dev.codeasmad.android.networking

import java.lang.Exception

sealed class Resource<Data> {
    class Success<Data>(data: Data) : Resource<Data>()
    class Failure<Data>(error: Exception) : Resource<Data>()
}
