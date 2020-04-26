package ru.sulgik.remotearduino.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.sulgik.remotearduino.database.FireDB.Companion.DOCUMENT_TYPE
import ru.sulgik.remotearduino.database.FireDB.Companion.log

class FireVM : ViewModel() {

    val db = FireDB()
    val firestore get() = db.firestore
    val auth get() = db.auth
    val user get() = auth.currentUser

    val userIsEmpty get()= user == null

    val savedDevices : MutableLiveData<List<FireDevice>> = MutableLiveData()
        get() {
            db.getSavedDevices().addSnapshotListener { snap, e ->
                if (e != null){
                    log.debug("devices aren't find", e.toString())
                    savedDevices.value = null
                    return@addSnapshotListener
                }
                val list = mutableListOf<FireDevice>()
                snap?.forEach {
                    val device = FireDevice.fromDocument(it)
                    if (device == null){
                        log.debug("Device == null")
                    }else{
                        list.add(device)
                    }
                }
                field.value = list
            }
            return field
        }
    val devicesIsEmpty get() = savedDevices.value == null

    fun deleteByMac(mac: String) = db.deleteByMacAddress(mac)

    fun deleteDevice(device : FireDevice) = db.deleteDevice(device)

    fun insertDevice(device: FireDevice) = db.insertDevice(device)

}