package ru.sulgik.remotearduino.modules.notify

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import androidx.annotation.StyleRes

data class Prebutton (val text : CharSequence, @StyleRes val style : Int, val listener : View.OnClickListener){
    fun asButton(context : Context): Button {
        return Button(ContextThemeWrapper(context, style), null, style).apply {
            setOnClickListener(listener)
        }
    }
}