package ru.sulgik.remotearduino.modules.tasks

import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.concurrent.Executor
import kotlin.Exception

class MutableTask <T>: Task<T>, Mutable<T>{

    private var isComplete = false
    private var result : T? = null
    private var exception : Exception? = null

    private val onCompleteListeners = mutableListOf<Listener<Task<T>>>()
    private val onSuccessListeners = mutableListOf<Listener<T>>()
    private val onFailedListeners = mutableListOf<Listener<Exception>>()


    override fun postValue(value: T) {
        exception = null
        result = value
        isComplete = true
        postCompleteListener()
        postSuccessListeners()
    }

    override fun postException(e: Exception) {
        exception = e
        result = null
        isComplete = true
        postCompleteListener()
        postFailedListener()
    }

    override fun postCompleteListener() {
        onCompleteListeners.forEach { listener ->
            listener.onListen(this)
        }
    }

    override fun postSuccessListeners() {
        result?.let {
            onSuccessListeners.forEach { listener ->
                listener.onListen(it)
            }
        }
    }

    override fun postFailedListener() {
        exception?.let {
            onFailedListeners.forEach { listener ->
                listener.onListen(it)
            }
        }
    }

    override fun removeListeners() {
        onCompleteListeners.clear()
        onSuccessListeners.clear()
        onFailedListeners.clear()
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

    override fun isComplete(): Boolean {
       return isComplete
    }

    override fun isSuccess(): Boolean {
        return result != null
    }

    override fun isFailed(): Boolean {
        return exception != null
    }

    override fun getResult(): T? {
        return result
    }

    override fun getException(): java.lang.Exception? {
        return exception
    }

    private fun autoPostCompleteListener(listener: Listener<Task<T>>){
        if(isComplete){
            listener.onListen(this)
        }
    }
    private fun autoPostSuccessListener(listener: Listener<T>){
        if(isSuccess){
            result?.let { listener.onListen(it) }
        }
    }
    private fun autoPostFailedListener(listener: Listener<java.lang.Exception>){
        if(isFailed){
            exception?.let { listener.onListen(it) }
        }
    }

    override fun addOnCompleteListener(listener: Listener<Task<T>>): Task<T> {
        autoPostCompleteListener(listener)
        onCompleteListeners.add(listener)
        return this
    }

    override fun addOnCompleteListener(
        owner: LifecycleOwner,
        listener: Listener<Task<T>>
    ): Task<T> {
        autoPostCompleteListener(Listenable(listener = listener, owner = owner))
        onCompleteListeners.add(Listenable(listener = listener, owner = owner))
        return this
    }

    override fun addOnCompleteListener(executor: Executor, listener: Listener<Task<T>>): Task<T> {
        autoPostCompleteListener(Listenable(listener = listener, executor = executor))
        onCompleteListeners.add(Listenable(listener = listener, executor = executor))
        return this
    }

    override fun addOnSuccessListener(listener: Listener<T>): Task<T> {
        autoPostSuccessListener(listener)
        onSuccessListeners.add(listener)
        return this
    }

    override fun addOnSuccessListener(owner: LifecycleOwner, listener: Listener<T>): Task<T> {
        autoPostSuccessListener(listener)
        onSuccessListeners.add(Listenable(listener = listener, owner = owner))
        return this
    }

    override fun addOnSuccessListener(executor: Executor, listener: Listener<T>): Task<T> {
        autoPostSuccessListener(Listenable(listener = listener, executor = executor))
        onSuccessListeners.add(Listenable(listener = listener, executor = executor))
        return this
    }

    override fun addOnFailedListener(listener: Listener<java.lang.Exception>): Task<T> {
        autoPostFailedListener(Listenable(listener))
        onFailedListeners.add(Listenable(listener = listener))
        return this
    }

    override fun addOnFailedListener(
        owner: LifecycleOwner,
        listener: Listener<java.lang.Exception>
    ): Task<T> {
        autoPostFailedListener(Listenable(listener = listener, owner = owner))
        onFailedListeners.add(Listenable(listener = listener, owner = owner))
        return this
    }

    override fun addOnFailedListener(
        executor: Executor,
        listener: Listener<java.lang.Exception>
    ): Task<T> {
        autoPostFailedListener(Listenable(listener = listener, executor = executor))
        onFailedListeners.add(Listenable(listener = listener,executor = executor))
        return this
    }


    companion object {
        fun <X, T> create(
            converter: Converter<X, T>,
            task: com.google.android.gms.tasks.Task<X>
        ): MutableTask<T> {
            return MutableTask<T>().setAttachedTask(converter, task)
        }

    }
}