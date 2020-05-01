package ru.sulgik.remotearduino.modules.notify

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.StyleRes
import ru.sulgik.remotearduino.R

class NotifyTask private constructor(){

    var layoutId = R.layout.default_notify_layout
    var title : String? = null
    var titleId = R.id.titleTv
    var text : String? = null
    var textId = R.id.textTv
    var image : Drawable? = null
    var imageId = R.id.vNotifyLogo
    var imageAnimation : Int = R.anim.alerter_pulse

    val buttons = mutableListOf<Prebutton>()
    var buttonsContainer = R.id.vButtonsContainer

    var duration = 2500000L

    fun Button(text : CharSequence, @StyleRes style : Int, listener: View.OnClickListener){
        buttons.add(Prebutton(text, style, listener))
    }
    fun Button(text : CharSequence, @StyleRes style : Int, listener: (View) -> Unit){
        Button(text, style, View.OnClickListener { listener.invoke(it) })
    }

    companion object{
        fun create(block : NotifyTask.() -> Unit): NotifyTask {
            return NotifyTask().apply(block)
        }
    }
}