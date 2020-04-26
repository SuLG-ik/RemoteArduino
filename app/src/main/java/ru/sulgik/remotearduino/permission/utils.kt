package ru.sulgik.remotearduino.permission

import android.content.Context
import androidx.core.content.ContextCompat

fun Array<out MaterialPermission>.asPathArray() = this.map { it.path }.toTypedArray()

fun Array<out MaterialPermission>.rebuild(context: Context) = Array(this.size){ this[it].rebuild(context)}