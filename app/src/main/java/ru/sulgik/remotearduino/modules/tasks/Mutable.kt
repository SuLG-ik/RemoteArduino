package ru.sulgik.remotearduino.modules.tasks

import com.google.android.gms.tasks.Task

interface Mutable<T> {

    fun postValue(value: T)

    fun postException(e: Exception)

    fun postCompleteListener()

    fun postSuccessListeners()

    fun postFailedListener()

    fun removeListeners()

    fun setAttachedTask(task: Task<T>) : MutableTask<T>

    fun <X> setAttachedTask(
        converter: Converter<X, T>,
        task: Task<X>
    ): MutableTask<T>

}