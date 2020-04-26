package ru.sulgik.remotearduino.notify

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import kotlinx.android.synthetic.main.default_notify_layout.view.*
import kotlinx.android.synthetic.main.notify_layout.view.*
import ru.sulgik.remotearduino.R

@SuppressLint("ViewConstructor")
class Notify(context: Context, @LayoutRes val layoutRes: Int, attr: AttributeSet? = null, defStyle: Int = 0)  : FrameLayout(context, attr, defStyle) {
    private var customInitializer : MutableList<((Notify) -> Unit)> = mutableListOf()

    private val logoAnimation = AnimationUtils.loadAnimation(context, R.anim.alerter_pulse)

    private val buttons = mutableListOf<AppCompatButton>()

    init {
        inflate(context, R.layout.default_notify_layout, this)
    }

    fun addCustomInitialization(func : (Notify) -> Unit) {
        customInitializer.add(func)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        vContentContainer.layoutResource = layoutRes
        vContentContainer.inflate()
    }

    private fun TextView.setNewText(text : CharSequence){
        if(!TextUtils.isEmpty(text)) {
            this.text = text
            this.visibility = View.VISIBLE
        }
    }

    fun setTitle(text: CharSequence) = titleTv.setNewText(text)
    fun setText(text: CharSequence) = textTv.setNewText(text)
    fun setDrawable(@DrawableRes res : Int) = setDrawable(context.getDrawable(res)!!)
    fun setDrawable(drawable : Drawable) = vNotifyLogo.setImageDrawable(drawable)
    fun setImageBitmap(image : Bitmap) = vNotifyLogo.setImageBitmap(image)

    fun addButton(text : CharSequence, style: Int, onClickListener: OnClickListener){
        val button = AppCompatButton(ContextThemeWrapper(context, style), null, style).apply {
            setText(text)
            setOnClickListener(onClickListener)
        }
        buttons.add(button)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        customInitializer.forEach {
            it.invoke(this)
        }

        vNotifyLogo.startAnimation(logoAnimation)
        val buttonsContainer = findViewById<LinearLayoutCompat>(R.id.vButtonsContainer)?: null
        if (buttonsContainer != null)
            buttons.forEach {
                buttonsContainer.addView(it)
            }
    }
}