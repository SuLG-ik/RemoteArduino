package ru.sulgik.remotearduino.modules.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.sulgik.remotearduino.modules.database.migration.Migration
import ru.sulgik.remotearduino.modules.database.migration.MigrationController

@Parcelize
data class RemoteDevice (
    val mac : String,
    val name : String,
    val id : String,
    val pictureUri : String?,
    val connectWay : Long
) : Parcelable{

    fun asDocument() : HashMap<Any, Any?> {
        return hashMapOf(
            EXTRA_MAC to mac,
            EXTRA_NAME to name,
            EXTRA_ID to id,
            EXTRA_CONNECT_WAY to connectWay,
            EXTRA_VERSION to LAST_VERSION
        )
    }

    companion object{
//         TODO() val migrator = MigrationController()

        fun fromDocument(map : HashMap<String,Any?>) : RemoteDevice {
//            TODO() migrator.migrate(map)
            return RemoteDevice(
                mac = map[EXTRA_MAC] as String,
                name = map[EXTRA_NAME] as String,
                id = map[EXTRA_ID] as String,
                connectWay = map[EXTRA_CONNECT_WAY] as Long,
                pictureUri = map[EXTRA_PICTURE_URI] as String?
            )
        }

        const val EXTRA_MAC = "mac_address"
        const val EXTRA_NAME = "displayed_name"
        const val EXTRA_ID = "id"
        const val EXTRA_CONNECT_WAY = "connect_way"
        const val EXTRA_VERSION = "version"
        const val EXTRA_PICTURE_URI = "picture_uri"

        const val LAST_VERSION = 1
        const val THE_OLDEST_SUPPORTED_VERSION = 1

        const val DB_CATALOG = "/devices"

        const val BLUETOOTH_DEVICE = 0
        const val WIFI_DEVICE = 1
    }

}
