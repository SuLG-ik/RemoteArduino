package ru.sulgik.remotearduino.modules.database.devices

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice

class DevicesViewModel(application: Application,
                       val repository : IDevicesRepository) :
    AndroidViewModel(application),
    IDevicesViewModel {

    override val allDevices = repository.getDevices()

    override fun getDeviceById(id : String) = repository.getDeviceById(id)

    override fun insert(device: RemoteDevice) = repository.insert(device)

    override fun deleteById(id : String) = repository.deleteDeviceById(id)

    override fun delete(device: RemoteDevice) = repository.delete(device)

}