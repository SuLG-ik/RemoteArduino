package ru.sulgik.remotearduino.modules.tasks

interface Converter<X, T> {

    fun convertForward(value: X) : T

    fun convertBackward(value: T) : X

    fun convertAllForward(list : Iterable<X>) = list.map { convertForward(it) }
    fun convertAllBackward(list : Iterable<T>) = list.map { convertBackward(it) }

}