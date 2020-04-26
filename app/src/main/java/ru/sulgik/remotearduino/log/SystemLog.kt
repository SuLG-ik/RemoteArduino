package ru.sulgik.remotearduino.log

import android.util.Log

class SystemLog(tag: String) : LogService(tag) {
    override fun warning(vararg msg: String?) {
        msg.forEachIndexed { index, s ->
            s?.let {Log.w("${tag}_$index", it)}
        }
    }

    override fun info(vararg msg: String?) {
        msg.forEachIndexed { index, s ->
            s?.let {Log.i("${tag}_$index", it)}
        }
    }

    override fun debug(vararg msg: String?) {
        msg.forEachIndexed { index, s ->
            s?.let {Log.d("${tag}_$index", it)}
        }
    }


}