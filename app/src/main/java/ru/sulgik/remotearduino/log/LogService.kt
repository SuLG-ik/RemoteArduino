package ru.sulgik.remotearduino.log

abstract class LogService(protected val tag: String) {

    abstract fun warning(vararg msg:String?)
    abstract fun info(vararg msg: String?)
    abstract fun debug(vararg msg:String?)

    override fun toString(): String {
        return "Log service $tag"
    }

}