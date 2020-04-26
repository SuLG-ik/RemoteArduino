package ru.sulgik.remotearduino.tasks

import androidx.lifecycle.LifecycleOwner
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executor

class MutableTask<T> : Task<T> {

    private var isComplete = false
    private var value : T? = null
    private var exception : Exception? = null

    private val onCompleteListeners = mutableListOf<Listenable<Task<T>>>()
    private val onSuccessListeners = mutableListOf<Listenable<T>>()
    private val onFailedListeners = mutableListOf<Listenable<Exception>>()

    fun postValue(value: T){
        this.value = value
        this.exception = null
        onCompleteListeners.forEach {
            it.post(this)
        }
        onSuccessListeners.forEach {
            it.post(value)
        }
    }

    fun postException(exception: Exception){
        this.value = null
        this.exception = exception
        onCompleteListeners.forEach {
            it.post(this)
        }
        onFailedListeners.forEach {
            it.post(exception)
        }
    }

    override fun isComplete(): Boolean {
        return isComplete
    }

    override fun isSuccess(): Boolean {
        return value != null
    }

    override fun isFailed(): Boolean {
        return exception == null
    }

    override fun getValue(): T? {
        return value
    }

    override fun getException(): Exception? {
        return exception
    }

    override fun addOnCompleteListener(listener: Listener<Task<T>>): Task<T> {
        onCompleteListeners.add(Listenable(listener))
        return this
    }
    override fun addOnCompleteListener(owner: LifecycleOwner, listener: Listener<Task<T>>): Task<T> {
        onCompleteListeners.add(Listenable(listener, owner))
        return this
    }
    override fun addOnCompleteListener(executor: Executor, listener: Listener<Task<T>>): Task<T> {
        onCompleteListeners.add(Listenable(listener = listener, executor = executor))
        return this
    }

    override fun addOnSuccessListener(listener: Listener<T>): Task<T> {
        onSuccessListeners.add(Listenable(listener))
        return this
    }
    override fun addOnSuccessListener(owner: LifecycleOwner, listener: Listener<T>): Task<T> {
        onSuccessListeners.add(Listenable(listener, owner))
        return this
    }
    override fun addOnSuccessListener(executor: Executor, listener: Listener<T>): Task<T> {
        onSuccessListeners.add(Listenable(listener = listener, executor = executor))
        return this
    }

    override fun addOnFailedListener(listener: Listener<Exception>): Task<T> {
        onFailedListeners.add(Listenable(listener = listener))
        return this
    }
    override fun addOnFailedListener(owner: LifecycleOwner, listener: Listener<Exception>): Task<T> {
        onFailedListeners.add(Listenable(listener = listener, owner = owner))
        return this
    }
    override fun addOnFailedListener(executor: Executor, listener: Listener<Exception>): Task<T> {
        onFailedListeners.add(Listenable(listener = listener, executor = executor))
        return this
    }

}