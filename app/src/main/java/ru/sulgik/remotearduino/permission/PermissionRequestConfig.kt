package ru.sulgik.remotearduino.permission

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class PermissionRequestConfig private constructor(
    var isCancelable : Boolean = false,
    var isBackgroundTransparent : Boolean = true
) {

    companion object{
        fun build(block : PermissionRequestConfig.() -> Unit = {}): PermissionRequestConfig {
            return PermissionRequestConfig().apply { block.invoke(this) }
        }
    }

}