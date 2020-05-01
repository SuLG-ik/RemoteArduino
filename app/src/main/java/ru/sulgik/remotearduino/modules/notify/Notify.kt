package ru.sulgik.remotearduino.modules.notify

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.notify_layout.view.*
import ru.sulgik.remotearduino.R

class Notify @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)  : FrameLayout(context, attr, defStyle) {
    private var customInitializer : MutableList<((Notify) -> Unit)> = mutableListOf()

    private val logoAnimation = AnimationUtils.loadAnimation(context, R.anim.alerter_pulse)

    private val buttons = mutableListOf<AppCompatButton>()

    init {
        inflate(context, R.layout.notify_layout, this)
    }

    fun show(task: NotifyTask){
        vContentContainer.layoutResource = task.layoutId
        vContentContainer.inflate()
        val title = findViewById<AppCompatTextView>(task.titleId) ?: null
        val text = findViewById<AppCompatTextView>(task.textId) ?: null
        val image = findViewById<AppCompatImageView>(task.imageId) ?: null
        val buttons = findViewById<ViewGroup>(task.buttonsContainer) ?: null


        title?.apply{
            if (task.title.isNullOrEmpty()){
                visibility = View.GONE
            }else{
                this.text = task.title
            }
        }
        text?.apply {
            if (task.text.isNullOrEmpty()){
                visibility = View.GONE
            }else{
                this.text = task.text
            }
        }
        image?.apply {
            if(task.image == null){
                visibility = View.GONE
            }else{
                this.setImageDrawable(task.image)
                startAnimation(AnimationUtils.loadAnimation(context, task.imageAnimation))
            }
        }

        if (buttons != null)
            task.buttons.forEach {
                buttons.addView(it.asButton(context))
            }


    }

    fun hide(){
        visibility = View.GONE
    }



    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        customInitializer.forEach {
            it.invoke(this)
        }
    }
}