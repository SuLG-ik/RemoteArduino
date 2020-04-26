package ru.sulgik.remotearduino.tasks

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor


class Listenable<T> @JvmOverloads constructor(private val listener: Listener<T>,
                    private val owner: LifecycleOwner? = null,
                    private val executor: Executor? = null) {

    private val ownerState get() = owner?.lifecycle?.currentState

    fun post(@NonNull data : T){
        if (ownerState == Lifecycle.State.RESUMED || ownerState == Lifecycle.State.CREATED || owner == null){
            executor?.execute{listener.onListen(data)} ?: listener.onListen(data)
        }
    }

}