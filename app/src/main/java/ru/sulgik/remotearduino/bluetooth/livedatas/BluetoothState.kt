package ru.sulgik.remotearduino.bluetooth.livedatas

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData

class BluetoothState private constructor(val context: Context): LiveData<Boolean>(BluetoothAdapter.getDefaultAdapter().isEnabled) {

    val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
    val receiver : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED){
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                value = state == BluetoothAdapter.STATE_ON
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
        var instance : BluetoothState? = null

        fun getInstance(context: Context) = instance ?: synchronized(this){
            instance = BluetoothState(context)
            instance!!
        }

    }
}