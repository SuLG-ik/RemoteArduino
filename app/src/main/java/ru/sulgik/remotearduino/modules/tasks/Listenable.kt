package ru.sulgik.remotearduino.modules.tasks

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor


class Listenable<T> @JvmOverloads constructor(private val listener: Listener<T>,
                    private val owner: LifecycleOwner? = null,
                    private val executor: Executor? = null)  : Listener<T>{

    private val ownerState get() = owner?.lifecycle?.currentState

    override fun onListen(@NonNull data : T){
        if (ownerState == Lifecycle.State.RESUMED || ownerState == Lifecycle.State.CREATED || owner == null){
            executor?.execute{listener.onListen(data)} ?: listener.onListen(data)
        }
    }

}