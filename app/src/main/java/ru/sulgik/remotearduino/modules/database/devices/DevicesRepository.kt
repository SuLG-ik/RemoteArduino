package ru.sulgik.remotearduino.modules.database.devices

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.util.Util
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.sulgik.remotearduino.modules.database.GeneralRepository
import ru.sulgik.remotearduino.modules.database.GeneralRepository.autoId
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice
import ru.sulgik.remotearduino.modules.database.live.livedata
import ru.sulgik.remotearduino.modules.tasks.MutableTask
import ru.sulgik.remotearduino.modules.tasks.Task
import ru.sulgik.remotearduino.modules.tasks.toOther
import java.lang.Exception

class DevicesRepository : IDevicesRepository {

    fun checkDeviceIdNotEmpty(device: RemoteDevice){
        if (device.id == "") {
            device.id = autoId()
        }
    }

    override fun getDevices() : LiveData<List<RemoteDevice>> = devicesLocation.livedata(RemoteDevice::class.java)

    private val devicesLocation get() =  GeneralRepository.userLocation.collection(RemoteDevice.LOCATION)

    override fun getDeviceById(id : String)   = devicesLocation.document(id).livedata(RemoteDevice::class.java)

    override fun insert(device : RemoteDevice) : Task<Void> {
        checkDeviceIdNotEmpty(device)
        return devicesLocation.document(device.id).set(device).toOther()
    }

    override fun delete(device: RemoteDevice): Task<Void> {
        return deleteDeviceById(device.id)
    }

    override fun deleteDeviceById(id : String): Task<Void> {
        return devicesLocation.document(id).delete().toOther()
    }

}