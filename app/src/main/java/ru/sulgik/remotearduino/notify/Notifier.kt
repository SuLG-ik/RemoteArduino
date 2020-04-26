package ru.sulgik.remotearduino.notify

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import ru.sulgik.remotearduino.R
import java.lang.ref.WeakReference

class Notifier private constructor(activity: AppCompatActivity, @IdRes attachedView : Int, @LayoutRes layoutRes: Int){

    private val notify = Notify(activity, layoutRes)

    init {
        Companion.activity = WeakReference(activity)
        Companion.attachedView = attachedView
    }

    fun show(): Notify {
        getActivity?.let {
            it.runOnUiThread{
                notifyLayout?.apply{
                    addView(notify)
                    visibility = View.VISIBLE
                }
            }
        }
        return notify
    }

    fun setTitle(title : CharSequence): Notifier {
        notify.setTitle(title)
        return this
    }
    fun setTitle(@StringRes title: Int)
            = setTitle(getActivity?.getString(title) ?: "")

    fun setText(text: CharSequence): Notifier {
        notify.setText(text)
        return this
    }
    fun setText(@StringRes text : Int)
            = setText(getActivity?.getString(text) ?: "")
    fun setDrawable(@DrawableRes res: Int): Notifier {
        notify.setDrawable(res)
        return this
    }
    fun setDrawable(drawable: Drawable) = notify.setDrawable(drawable)
    fun setImageBitmap(bm : Bitmap): Notifier {
        notify.setImageBitmap(bm)
        return this
    }

    fun setOnClickListener(listener : View.OnClickListener): Notifier {
        notify.setOnClickListener(listener)
        return this
    }


    companion object{

        @IdRes
        private var attachedView : Int = 0

        private var activity : WeakReference<AppCompatActivity>? = null
        private val getActivity
            get() = activity?.get()

        private val notifyLayout : ViewGroup?
            get() = getActivity?.findViewById(attachedView)



        @JvmStatic
        fun create(activity: AppCompatActivity,
                   @IdRes layout: Int,
                   @LayoutRes layoutRes : Int = R.layout.default_notify_layout): Notifier {
            hide()
            return Notifier(activity, layout, layoutRes)
        }

        @JvmStatic
        fun hide(){
            activity?.get()?.let {
                it.runOnUiThread {
                    notifyLayout?.apply{
                        visibility = View.GONE
                        removeAllViews()
                    }
                }
            }
        }
    }

}