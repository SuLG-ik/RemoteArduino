package ru.sulgik.remotearduino.bluetooth.requestdevice

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.arch.core.util.Function
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.RemoteArduinoActivity
import ru.sulgik.remotearduino.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.bluetooth.SelectedDeviceActivity
import ru.sulgik.remotearduino.databinding.ActivityDevicesInitBinding
import ru.sulgik.remotearduino.log.LogService
import ru.sulgik.remotearduino.log.SystemLog
import ru.sulgik.remotearduino.permission.MaterialPermissionManager

class DevicesInitActivity : RemoteArduinoActivity(R.layout.activity_devices_init) {

    override val TAG: String = DEVICES_INIT_ACTIVITY_TAG

    val binding by viewDataBindings<ActivityDevicesInitBinding>(resId)

    val bluetooth : BluetoothManager = BluetoothManager()

    private val bluetoothDevices = MediatorLiveData<List<BluetoothDevice>>()
    private val toShowDevices = Transformations.map(bluetoothDevices, Function <List<BluetoothDevice>, List<DeviceToShowItem>> { devices ->
        return@Function devices.map { DeviceToShowItem(it) }
    })

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        bluetooth.onRequest(this,requestCode,resultCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionManager = MaterialPermissionManager()
        binding.lifecycleOwner = this

        val adapter = DevicesAdapter()

        binding.btList.apply {
            layoutManager = LinearLayoutManager(this@DevicesInitActivity).apply {
                reverseLayout = true
            }
            this.adapter = adapter
        }

        permissionManager.with(this, getBluetoothDiscoveringPermissions(this)){

            //BL
            //Discovering
            bluetooth.getDiscoveringState(this).observe(this, Observer {
                it?.let {
                    discoveringState(it)
                    log.debug("state", lifecycle.currentState.toString())
                    log.debug("discovering state ",it.toString())
                }
            })
            //State
            bluetooth.getBluetoothState(this).observe(this, Observer {
                it?.let {
                    bluetoothState(it)
                }
            })

            //UI
            //Bluetooth state
            binding.switcher.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    bluetooth.activate(this)
                }
                else {
                    bluetooth.disable()
                }
            }
            binding.progressLayout.setOnClickListener {
                bluetooth.startDiscovery()
            }

            bluetooth.getDiscoveredDevices(this).observe(this){
                adapter.submitList((bluetooth.boundedDevices + it).map { DeviceToShowItem(it) } )
            }

            adapter.setOnDeviceClickListener { position, device, view : View ->
                val intent = Intent(this, SelectedDeviceActivity::class.java)
                val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view)!!)
                startActivity(intent.apply { putExtra("EXTRA_DEVICE", device) }, option.toBundle())
            }

        }

    }

    val names get() =  listOf(
        getString(R.string.device_name_1),
        getString(R.string.device_name_2),
        getString(R.string.device_name_3),
        getString(R.string.device_name_4)
    )

    private fun discoveringState(state : Boolean){
        binding.discoveringState.visibility = if (state) View.VISIBLE else View.GONE
    }
    private fun bluetoothState(state: Boolean){
        binding.switcher.isChecked = state
    }


    companion object{

        val log: LogService = SystemLog("LogDevicesInitActivity")

        const val DEVICES_INIT_ACTIVITY_TAG = "DevicesInitActivity:789123"
    }
}
