package ru.sulgik.remotearduino.bluetooth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.RemoteArduinoActivity
import ru.sulgik.remotearduino.bluetooth.requestdevice.DeviceToShowItem
import ru.sulgik.remotearduino.databinding.ActivitySelectedDeviceBinding

class SelectedDeviceActivity : RemoteArduinoActivity(R.layout.activity_selected_device) {

    val binding by viewDataBindings<ActivitySelectedDeviceBinding>(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        binding.name.text = intent.getParcelableExtra<DeviceToShowItem>("EXTRA_DEVICE")!!.name
    }

    override val TAG: String
        get() = "selected_device_activity"
}
