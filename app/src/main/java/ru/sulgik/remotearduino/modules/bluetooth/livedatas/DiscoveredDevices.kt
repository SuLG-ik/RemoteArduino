package ru.sulgik.remotearduino.modules.bluetooth.livedatas

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData

class DiscoveredDevices private constructor(val context: Context) : LiveData<List<BluetoothDevice>>(emptyList()) {

    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND).apply { addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED) }
    val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == BluetoothAdapter.ACTION_DISCOVERY_STARTED){
                value = emptyList()
            }
            if (intent?.action == BluetoothDevice.ACTION_FOUND){
                try {
                val list = value?.toMutableList() ?: mutableListOf()
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                if(device !=null){
                    list.add(device)
                    value = list
                }
                }catch (e:Exception){
                    e.printStackTrace()
                }
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
        var instance : DiscoveredDevices? = null

        fun getInstance(context: Context) = instance
            ?: synchronized(this){
            instance =
                DiscoveredDevices(
                    context
                )
            instance!!
        }

    }

}

