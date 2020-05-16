package ru.sulgik.remotearduino.modules.database.devices

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice

class DevicesViewModel(application: Application) :
    AndroidViewModel(application), KoinComponent{


    private val repository : DevicesRepository by inject()

    val allDevices = repository.getDevices()

    fun getDeviceById(id : String) = repository.getDeviceById(id)

    fun insert(device: RemoteDevice) = repository.insert(device)

    fun deleteById(id : String) = repository.deleteDeviceById(id)

    fun delete(device: RemoteDevice) = repository.delete(device)

}