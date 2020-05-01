package ru.sulgik.remotearduino.modules.bluetooth.livedatas

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData

class DiscoveringState private  constructor(val context: Context) : LiveData<Boolean>(BluetoothAdapter.getDefaultAdapter().isDiscovering) {

    val filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED).apply { addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) }
    val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothAdapter.ACTION_DISCOVERY_STARTED){
                value = true
            }
            if (intent?.action == BluetoothAdapter.ACTION_DISCOVERY_FINISHED){
                value =  false
            }
        }
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(receiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(receiver)
    }

    companion object{

        @Volatile
        var instance : DiscoveringState? = null

        fun getInstance(context: Context) = instance
            ?: synchronized(this){
            instance =
                DiscoveringState(context)
            instance!!
        }

    }

}