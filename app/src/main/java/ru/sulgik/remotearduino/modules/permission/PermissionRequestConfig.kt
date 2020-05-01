package ru.sulgik.remotearduino.modules.permission

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