package ru.sulgik.remotearduino.bluetooth

import android.app.Activity
import ru.sulgik.remotearduino.Receiver
import android.bluetooth.BluetoothAdapter
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import ru.sulgik.remotearduino.log.SystemLog
import android.content.BroadcastReceiver
import androidx.annotation.CallSuper
import ru.sulgik.remotearduino.BaseLifecycleObserver
import ru.sulgik.remotearduino.ReceiverLifecycleObserver
import ru.sulgik.remotearduino.bluetooth.livedatas.BluetoothState
import ru.sulgik.remotearduino.bluetooth.livedatas.DiscoveredDevices
import ru.sulgik.remotearduino.bluetooth.livedatas.DiscoveringState

class BluetoothManager {

    private val bluetooth = BluetoothAdapter.getDefaultAdapter()
    val isValid get() = bluetooth != null
    val state get() = bluetooth.state
    val isActivated get() = state == BluetoothAdapter.STATE_ON
    val isDiscovering get() = bluetooth.isDiscovering

    fun sendData(context: Context, data : BluetoothDataToSend) {
        context.startService(SenderService.intentToSend(data))
    }

    fun activate(activity: AppCompatActivity) {
        if (!isActivated)
            activity.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_BT_ENABLE)
    }


    fun onRequest(context: Context, reqCode : Int, result: Int){
        if(reqCode == REQUEST_BT_ENABLE){
            if(result == Activity.RESULT_CANCELED){
                getBluetoothState(context).receiver.onReceive(context, Intent(BluetoothAdapter.ACTION_STATE_CHANGED).putExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF))
            }
        }

    }

    fun disable(){
        if (isActivated)
            bluetooth.disable()
    }

    fun startDiscovery() {
        if (!isDiscovering)
            bluetooth.startDiscovery()
    }

    fun stopDiscovery(){
        log.debug("stopDiscovery")
        bluetooth.cancelDiscovery()
    }

    fun getDeviceByAddress(address : String) = bluetooth.getRemoteDevice(address)

    val boundedDevices get() = bluetooth.bondedDevices.toList()

    private val bluetoothServiceReceiver : Receiver = object : Receiver(SenderService.BROADCAST) {

        override fun onInvalid(intent: Intent) {
            bluetoothServiceListener.onInvalid(intent)
        }

        override fun onCanceled(intent: Intent) {
            bluetoothServiceListener.onCanceled(intent)
        }

        override fun onDone(intent: Intent) {
            bluetoothServiceListener.onDone(intent)
        }

    }
    private var bluetoothServiceListener : BluetoothServiceListener = object : BluetoothServiceListener{
        override fun onInvalid(intent: Intent) {
        }

        override fun onCanceled(intent: Intent) {
        }

        override fun onDone(intent: Intent) {
        }

    }
    fun setOnBluetoothServiceListener(activity: AppCompatActivity, listener: BluetoothServiceListener){

        activity.lifecycle.addObserver(ReceiverLifecycleObserver(activity, bluetoothServiceReceiver, bluetoothServiceReceiver.filter))

        bluetoothServiceListener = listener
    }
    fun setOnBluetoothServiceListener(activity: AppCompatActivity, listener: (isValid : Boolean, isDone : Boolean, intent : Intent) -> Unit){
        setOnBluetoothServiceListener(activity, object : BluetoothServiceListener{
            override fun onInvalid(intent: Intent) {
                listener.invoke(false, false, intent)
            }

            override fun onCanceled(intent: Intent) {
               listener.invoke(true, false, intent)
            }

            override fun onDone(intent: Intent) {
                listener.invoke(true, true, intent)
            }

        })

    }

    fun getBluetoothState(context: Context) = BluetoothState.getInstance(context)

    fun getDiscoveringState(context: Context) = DiscoveringState.getInstance(context)
    fun getDiscoveredDevices(context: Context): DiscoveredDevices {
        startDiscovery()
        return DiscoveredDevices.getInstance(context)
    }

    interface BluetoothServiceListener{

        fun onInvalid(intent: Intent)

        fun onCanceled(intent: Intent)

        fun onDone(intent: Intent)

    }


    companion object{

        const val REQUEST_BT_ENABLE = 156

        const val ON_CHANGE_STATE_RECEIVER = "BluetoothService:ON_CHANGE_STATE_RECEIVER"

        val log = SystemLog("BluetoothService")

    }

}