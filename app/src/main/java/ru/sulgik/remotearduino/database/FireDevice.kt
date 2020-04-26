package ru.sulgik.remotearduino.database

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ru.sulgik.remotearduino.database.FireDB.Companion.DOCUMENT_VERSION
import ru.sulgik.remotearduino.database.exceptions.CastDocumentTypeException
import java.lang.Exception
import java.lang.NullPointerException

@Parcelize
data class FireDevice (val name : String, val mac : String, val type : Long, val version : Long = LAST_VERSION)  : Parcelable{

    constructor(device: BluetoothDevice, type : Long) : this(device.name, device.address, type)

    fun asDocument() =
                hashMapOf(
                    NAME to name,
                    MAC to mac,
                    DEVICE_TYPE to type,
                    FireDB.DOCUMENT_TYPE to DOCUMENT_TYPE,
                    DOCUMENT_VERSION to version
                    )


    fun asDevice(adapter : BluetoothAdapter) = adapter.getRemoteDevice(mac)

    companion object{
        const val DOCUMENT_TYPE = 1L
        const val LAST_VERSION = 1L

        const val NAME = "name"
        const val MAC = "mac"
        const val DEVICE_TYPE = "device_type"

        fun fromMap(map : Map<String, Any>) = try {
            if (map[FireDB.DOCUMENT_TYPE] as Long != DOCUMENT_TYPE) throw CastDocumentTypeException("FireDevice")
            val name = map[NAME] as String
            val mac = map[MAC] as String
            val type = map[DEVICE_TYPE] as Long
            val version = map[DOCUMENT_VERSION] as Long
            FireDevice(name, mac, type, version = version)
        }catch (e: Exception){
            FireDB.log.warning(e.toString())
            null
        }

        fun fromDocument(document : QueryDocumentSnapshot): FireDevice? = fromMap(document.data)
    }

}