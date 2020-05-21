package ru.sulgik.test.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.sulgik.test.common.coroutines.UseCase
import ru.sulgik.test.common.events.EventResult
import java.lang.Exception

typealias OnSuccessListener<T> = (value : T) -> Unit
typealias OnLoadingListener = () -> Unit
typealias OnFailureListener= (e : Exception) -> Unit
typealias OnCancelListener = () -> Unit

typealias MutableLiveEvent <T> = MutableLiveData<EventResult<T>>
typealias LiveEvent<T> = LiveData<EventResult<T>>

fun <T>EventResult<T>.doIfSuccess(block : (result : T) -> Unit){
    if (this.isSuccess){
        block.invoke(this.getOrNull!!)
    }
}

fun <T>EventResult<T>.doIfFailure(block : (result : Exception) -> Unit) {
    if (this.isFailure) {
        block.invoke(this.exceptionOrNull!!)
    }
}

fun <T>EventResult<T>.doIfCancel(block : () -> Unit) {
    if (this.isCancel) {
        block.invoke()
    }
}

fun <T>EventResult<T>.doIfStart(block : () -> Unit) {
    if (this.isLoading) {
        block.invoke()
    }
}


fun <T> MutableLiveEvent<T>.postResult(result : T){
    postValue(EventResult.success(result))
}

fun <T> MutableLiveEvent<T>.postException(exception : Exception){
    postValue(EventResult.failure(exception))
}

fun <T> MutableLiveEvent<T>.postLoading(){
    postValue(EventResult.loading())
}

fun <T> MutableLiveEvent<T>.postCancel(){
    postValue(EventResult.cancel())
}

inline fun <Type, Params> usecase(crossinline block : suspend (Params) -> Type): UseCase<Type, Params> {
    return object : UseCase<Type, Params>(){
        override suspend fun run(params: Params): Type {
            return block.invoke(params)
        }
    }
}