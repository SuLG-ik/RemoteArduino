package ru.sulgik.remotearduino.modules.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RemoteDevice (
    val mac : String,
    val name : String,
    val id : Long,
    val connectWay : Long,
    val pictureUri : Long
) : Parcelable{

    fun asDocument() : HashMap<Any, Any> {
        return hashMapOf(
            EXTRA_MAC to mac,
            EXTRA_NAME to name,
            EXTRA_ID to id,
            EXTRA_CONNECT_WAY to connectWay,
            EXTRA_VERSION to LAST_VERSION
        )
    }

    companion object{

        fun fromDocument(map : HashMap<Any,Any>) : RemoteDevice {
            TODO("from document")
        }

        const val EXTRA_MAC = "mac_address"
        const val EXTRA_NAME = "displayed_name"
        const val EXTRA_ID = "id"
        const val EXTRA_CONNECT_WAY = "connect_way"
        const val EXTRA_VERSION = "version"
        const val EXTRA_PICTURE_URI = "picture_uri"

        const val LAST_VERSION = 1
        const val THE_OLDEST_SUPPORTED_VERSION = 1

        const val DB_PATH = "/devices"

        const val BLUETOOTH_DEVICE = 0
        const val WIFI_DEVICE = 1
    }

}
