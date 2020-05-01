package ru.sulgik.remotearduino.modules.database.devices

import androidx.lifecycle.LiveData
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice
import ru.sulgik.remotearduino.modules.tasks.Task

interface IDevicesRepository {

    fun getDevices() : LiveData<List<RemoteDevice>>

    fun getDeviceById(id : String) : LiveData<Result<RemoteDevice>>

    fun insert(device : RemoteDevice) : Task<Void>

    fun delete(device: RemoteDevice): Task<Void> {
        return deleteDeviceById(device.id)
    }

    fun deleteDeviceById(id : String): Task<Void>

}