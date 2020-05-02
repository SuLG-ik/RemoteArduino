package ru.sulgik.remotearduino.modules.permission


import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ru.sulgik.remotearduino.R
import java.io.Serializable

@Parcelize
data class MaterialPermission (val path: String, @PermissionChecker.PermissionResult var state : Int, val name: String, val description : String= "", val logo : Int = R.drawable.ic_error_outline_black_24dp, val logo2: Int = logo ,val necessary : Int = UNIMPORTANT) : Parcelable, Serializable{

    constructor(context: Context, path: String, @StringRes name : Int, @StringRes description: Int, @DrawableRes logo: Int, @DrawableRes logo2 : Int = logo, necessary : Int = UNIMPORTANT)
            : this(path, ContextCompat.checkSelfPermission(context, path), context.getString(name), context.getString(description), logo, logo2, necessary)

    fun rebuild(context: Context): MaterialPermission {state = ContextCompat.checkSelfPermission(context, path); return this}

    @IgnoredOnParcel
    var isSelected = false
    fun inverseSelectable() { isSelected = !isSelected }

    companion object {
        const val IMPORTANT = -1
        const val UNIMPORTANT = 0
    }
}
