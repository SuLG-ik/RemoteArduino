package ru.sulgik.remotearduino.database

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.sulgik.remotearduino.database.FireDevice.Companion.MAC
import ru.sulgik.remotearduino.database.exceptions.CastDocumentTypeException
import ru.sulgik.remotearduino.log.SystemLog
import java.lang.Exception

class FireDB {

    val firestore = Firebase.firestore
    val auth = FirebaseAuth.getInstance()
    val user get() = auth.currentUser

    val userIsEmpty get() = user == null

    private val userPath = MAIN_PATH + user?.uid + "/"

    private val devicesPath = userPath + DEVICES_PATH

    fun insertDevice(device: FireDevice) = toPath(devicesPath).document().set(device.asDocument())

    fun deleteDevice(device: FireDevice) = deleteByMacAddress(device.mac)
    fun deleteByMacAddress(mac : String) = getSavedDevices().whereEqualTo(FireDevice.MAC, mac).get().addOnSuccessListener { docs ->
        docs.forEach { doc ->
            doc.reference.delete()
        }
    }

    fun getSavedDevices() = toPath(devicesPath)

    fun toPath(path : String) = firestore.collection(path)

    companion object{
        val log = SystemLog("FireDB")

        const val DOCUMENT_TYPE = "document_type"
        const val DOCUMENT_VERSION = "document_version"

        const val MAIN_PATH = "users/"
        const val DEVICES_PATH = "devices/"

    }

}