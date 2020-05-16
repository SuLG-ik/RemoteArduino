package ru.sulgik.remotearduino.modules.delegate

import android.app.Activity
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBinderMapperImpl
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.channels.Channel
import ru.sulgik.remotearduino.ui.RemoteArduinoActivity
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.viewBindings(factory: (View) -> T) = DelegateFragment(factory)

class DelegateFragment <out T : ViewBinding>(private val factory : (View) -> T) : ReadOnlyProperty<Fragment, T>{

    private var binding : T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T = binding ?: synchronized(this){
        return factory.invoke(thisRef.requireView()).also {
            binding = it
        }
    }
}

@MainThread
fun <T : ViewDataBinding> Activity.viewBindings(@LayoutRes resId: Int) = DataBindingPropertyByRes<T>(resId)
fun <T : ViewDataBinding> RemoteArduinoActivity.viewBindings() = DataBindingPropertyByRes<T>(this.resId)

class DataBindingPropertyByRes<out T : ViewDataBinding>(@LayoutRes private val resId: Int) :
    ReadOnlyProperty<Activity, T> {
    private var binding: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T =
        binding ?: DataBindingUtil.setContentView<T>(thisRef, resId).also {
            binding = it
        }
}