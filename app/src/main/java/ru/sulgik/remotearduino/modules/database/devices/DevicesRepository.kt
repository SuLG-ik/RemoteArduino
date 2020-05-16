package ru.sulgik.remotearduino.modules.database.devices

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulgik.remotearduino.modules.authentication.AuthKeyOwner
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.modules.database.live.livedata
import ru.sulgik.remotearduino.modules.database.utils.autoId
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice
import ru.sulgik.remotearduino.modules.tasks.Task
import ru.sulgik.remotearduino.modules.tasks.toOther

class DevicesRepository : KoinComponent {

    val owner : AuthKeyOwner by inject<AuthService>()

    val location get() = Firebase.firestore.collection(LOCATION_USERS).document(owner.key)

    private fun checkDeviceIdNotEmpty(device: RemoteDevice){
        if (device.id == "") {
            device.id = autoId()
        }
    }

    fun getDevices() : LiveData<List<RemoteDevice>> = devicesLocation.livedata(RemoteDevice::class.java)

    private val devicesLocation get() =  location.collection(RemoteDevice.LOCATION)

    fun getDeviceById(id : String)   = devicesLocation.document(id).livedata(RemoteDevice::class.java)

    fun insert(device : RemoteDevice) : Task<Void> {
        checkDeviceIdNotEmpty(device)
        return devicesLocation.document(device.id).set(device).toOther()
    }

    fun delete(device: RemoteDevice): Task<Void> {
        return deleteDeviceById(device.id)
    }

    fun deleteDeviceById(id : String): Task<Void> {
        return devicesLocation.document(id).delete().toOther()
    }

    companion object{
        const val LOCATION_USERS = "users/"
    }

}