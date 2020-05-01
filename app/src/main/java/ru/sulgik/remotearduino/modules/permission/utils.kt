package ru.sulgik.remotearduino.modules.permission

import android.content.Context

fun Array<out MaterialPermission>.asPathArray() = this.map { it.path }.toTypedArray()

fun Array<out MaterialPermission>.rebuild(context: Context) = Array(this.size){ this[it].rebuild(context)}