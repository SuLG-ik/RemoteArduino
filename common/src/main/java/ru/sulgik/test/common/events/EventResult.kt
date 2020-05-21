package ru.sulgik.test.common.events

import java.lang.Exception

class EventResult <out T> private constructor(
    private val result : Result
){

    companion object{
        fun <T> success(value : T) = EventResult<T>(Success(value))
        fun <T> failure(exception: Exception) = EventResult<T>(Failure(exception))
        fun <T> loading() = EventResult<T>(Loading())
        fun <T> preparing() = EventResult<T>(Preparing())
        fun <T> cancel() = EventResult<T>(Cancel())
    }

    val isSuccess = result is Success<*>

    val isFailure = result is Failure

    val isLoading = result is Loading

    val isPreparing = result is Preparing

    val isCancel = result is Cancel

    @Suppress("UNCHECKED_CAST")
    val getOrNull : T? get() = result.result as? T

    val exceptionOrNull : Exception? get() = result.result as? Exception

    class Success<T>(val value : T) : Result(value)
    class Failure(val exception: Exception) : Result(exception)
    class Loading : Result(null)
    class Preparing : Result(null)
    class Cancel : Result(null)

    abstract class Result(val result : Any?)

}

