package ru.sulgik.remotearduino.ui.devices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import org.koin.android.viewmodel.ext.android.sharedViewModel
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentDevicesBinding
import ru.sulgik.remotearduino.modules.database.devices.DevicesViewModel
import ru.sulgik.remotearduino.modules.delegate.viewBindings

class DevicesFragment : Fragment(R.layout.fragment_devices) {

    val devices : DevicesViewModel by sharedViewModel()

    val binding by viewBindings(FragmentDevicesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.addDevice.setOnClickListener {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
        }


    }

}
