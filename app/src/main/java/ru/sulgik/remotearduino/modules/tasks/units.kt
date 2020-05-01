package ru.sulgik.remotearduino.modules.tasks


fun <T> com.google.android.gms.tasks.Task<T>.toOther(): Task<T> {
    return MutableTask<T>().setAttachedTask(this)
}

fun <X, T> com.google.android.gms.tasks.Task<X>.toOther(converter: Converter<X,T>): Task<T> {
    return MutableTask<T>().setAttachedTask(converter, this)
}