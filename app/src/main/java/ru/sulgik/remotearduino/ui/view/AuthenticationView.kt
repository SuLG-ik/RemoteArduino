package ru.sulgik.remotearduino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat

class AuthenticationView @JvmOverloads constructor(context : Context, attr :AttributeSet? = null, style : Int = 0) : LinearLayoutCompat(context, attr, style){

    val FIELDS: Array<View> = arrayOf(

    )


    override fun getOrientation(): Int {
        return VERTICAL
    }

    override fun getLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

}