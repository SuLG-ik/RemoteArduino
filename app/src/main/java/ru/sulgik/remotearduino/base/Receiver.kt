package ru.sulgik.remotearduino.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.CallSuper

abstract class Receiver(val actions: MutableList<String>) : BroadcastReceiver() {

    constructor(action : String) : this(mutableListOf(action))

    @CallSuper
    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent != null)
            if (intent.action in actions){
                val result = intent.getIntExtra(
                    RESULT,
                    RESULT_INVALID_INTENT
                )
                when (result){
                    RESULT_OK -> onDone(intent)
                    RESULT_CANCELED -> onCanceled(intent)
                    else -> {
                        onInvalid(intent)
                    }
                }
            }

    }

    abstract fun onInvalid(intent: Intent)
    abstract fun onCanceled(intent: Intent)
    abstract fun onDone(intent: Intent)

    fun addFilter(action : String): IntentFilter {
        actions.add(action)
        return filter
    }

    val filter get() = IntentFilter().apply { actions.forEach {addAction(it)} }

    companion object{
        const val RESULT = "ru.sulgik.remotearduino.modules.bluetooth:BROADCAST:RESULT_STATE:756851"
        const val RESULT_OK = 2
        const val RESULT_INVALID_INTENT = 0
        const val RESULT_CANCELED = -1

        fun baseIntent(action: Intent, result: Int): Intent = action.putExtra(RESULT, result)
    }
}