package ru.sulgik.remotearduino.modules.events

import androidx.lifecycle.*
import org.greenrobot.eventbus.EventBus

fun EventBus.bindToLifecycle(owner: LifecycleOwner, subscriber : Any?){
    owner.lifecycle.addObserver(object : LifecycleObserver{
       @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
       fun reg(){
           this@bindToLifecycle.register(subscriber)
       }
       @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
       fun unreg(){
           this@bindToLifecycle.unregister(subscriber)
       }
   })
}

fun EventBus.bindToLifecycle(owner: LifecycleOwner) = this.bindToLifecycle(owner,owner)