package ru.sulgik.remotearduino.modules.bluetooth

import android.app.IntentService
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import ru.sulgik.remotearduino.base.Receiver.Companion.RESULT_INVALID_INTENT
import ru.sulgik.remotearduino.base.Receiver.Companion.RESULT_OK
import ru.sulgik.remotearduino.base.Receiver.Companion.baseIntent
import ru.sulgik.remotearduino.modules.log.SystemLog

class SenderService : IntentService("BluetoothService") {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log.debug("onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        log.debug("Try to do task")
        val bluetooth = BluetoothAdapter.getDefaultAdapter()
        try{
            val data = BluetoothDataToSend.fromBundle(intent?.extras)

            sendCallback(RESULT_OK, data)
        }catch (e : Throwable){
            log.debug("task error")
            e.printStackTrace()
            sendCallback(RESULT_INVALID_INTENT,null)
        }
        log.debug("wait next task")
    }

    fun sendCallback(result: Int, data: BluetoothDataToSend?){
        if(result == RESULT_INVALID_INTENT) {
            sendBroadcast(broadcastIntent)
            return
        }
        if(data == null) {
            sendBroadcast(broadcastIntent)
            return
        }

        sendBroadcast(baseIntent(broadcastIntent, result).intentToSend(data))
    }

    override fun onDestroy() {
        log.debug("Destroy")
        super.onDestroy()
    }

    override fun onCreate() {
        super.onCreate()
        log.debug("Service started")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return BluetoothBinder()
    }

    inner class BluetoothBinder : Binder() {
        val service: SenderService
            get() = this@SenderService
    }


    companion object{
        val log = SystemLog("BluetoothService")

        val asIntent get() = Intent("ru.sulgik.remotearduino.modules.bluetooth.BluetoothService").setPackage("ru.sulgik.remotearduino")

        fun intentToSend(data : BluetoothDataToSend) = asIntent.intentToSend(data)
        private fun Intent.intentToSend(data : BluetoothDataToSend) = this.putExtra(BluetoothDataToSend.EXTRA, data)

        const val BROADCAST = "ru.sulgik.remotearduino.modules.bluetooth:BluetoothService:BROADCAST:999999"
        val broadcastIntent get() = Intent(BROADCAST)
    }

}
