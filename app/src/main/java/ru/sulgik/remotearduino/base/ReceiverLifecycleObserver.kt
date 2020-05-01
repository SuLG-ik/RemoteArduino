package ru.sulgik.remotearduino.base

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import ru.sulgik.remotearduino.base.BaseLifecycleObserver

class ReceiverLifecycleObserver(val activity: AppCompatActivity, val receiver: BroadcastReceiver, val filter: IntentFilter) :
    BaseLifecycleObserver {
    override fun onCreate() {
        activity.registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        activity.unregisterReceiver(receiver)
    }
}