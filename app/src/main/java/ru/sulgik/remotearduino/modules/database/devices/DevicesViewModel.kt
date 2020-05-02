package ru.sulgik.remotearduino.modules.database.devices

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice

class DevicesViewModel(application: Application,
                       val repository : DevicesRepository) :
    AndroidViewModel(application){

    val allDevices = repository.getDevices()

    fun getDeviceById(id : String) = repository.getDeviceById(id)

    fun insert(device: RemoteDevice) = repository.insert(device)

    fun deleteById(id : String) = repository.deleteDeviceById(id)

    fun delete(device: RemoteDevice) = repository.delete(device)

}