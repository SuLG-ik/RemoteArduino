package ru.sulgik.remotearduino.ui.devices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.device_item.*
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentDevicesBinding
import ru.sulgik.remotearduino.modules.database.devices.DevicesViewModel
import ru.sulgik.remotearduino.modules.database.devices.IDevicesViewModel
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice

class DevicesFragment : Fragment() {

    val devices : IDevicesViewModel by sharedViewModel<DevicesViewModel>()

    var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentDevicesBinding>(inflater, R.layout.fragment_devices, container, false)

        binding.addDevice.setOnClickListener {
            devices.insert(RemoteDevice(name = count.toString()))
            count++
        }

        return binding.root
    }

}
