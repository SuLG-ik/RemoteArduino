package ru.sulgik.remotearduino.tasks

import androidx.annotation.CallSuper
import java.lang.Exception

open class TaskController <T> {

    open var task : MutableTask<T>? = null

    @CallSuper
    fun postValue(value: T){
        task?.postValue(value)
        task = null
    }

    @CallSuper
    fun postException(exception: Exception){
        task?.postException(exception)
        task = null
    }

    fun newTask(): Task<T> = synchronized(this) {
            task = MutableTask()
            return task!!
        }
}