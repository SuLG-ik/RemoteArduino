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
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.viewBindings(factory: (View) -> T) = Delegate(this,factory)

class Delegate <out T : ViewBinding>(owner: LifecycleOwner, private val factory : (View) -> T) : ReadOnlyProperty<Fragment, T>{

    private var binding : T? = null

    init {
        owner.lifecycle.addObserver(object : LifecycleObserver{
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun unit(){
                binding = null
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }

        if(!thisRef.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)){
            throw IllegalStateException("Cant get binding when fragment destroyed")
        }

        return factory(thisRef.requireView()).also { binding = it }
    }
}

@MainThread
fun <T : ViewDataBinding> Activity.viewBindings(@LayoutRes resId: Int) = DataBindingProperty<T>(resId)

class DataBindingProperty<out T : ViewDataBinding>(@LayoutRes private val resId: Int) :
    ReadOnlyProperty<Activity, T> {
    private var binding: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T =
        binding ?: DataBindingUtil.setContentView<T>(thisRef, resId).also {
            binding = it
        }
}