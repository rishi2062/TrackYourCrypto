package com.example.scogoass.handler

sealed class ResultHandler<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultHandler<T>(data)
    class Error<T>(message: String, data: T?) : ResultHandler<T>(null, message)
}