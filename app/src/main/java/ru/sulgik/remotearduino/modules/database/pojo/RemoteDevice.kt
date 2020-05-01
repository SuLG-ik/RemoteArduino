package ru.sulgik.remotearduino.modules.database.pojo

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.koin.ext.getScopeId

@Parcelize
data class RemoteDevice (
    val mac : String = "",
    val name : String = "",
    val connectWay : Long = -1,
    val pictureUri : String? = null,
    var id : String = ""
) : Parcelable{

    companion object{
        const val LOCATION = "/devices"

        const val BLUETOOTH_DEVICE = 0
        const val WIFI_DEVICE = 1
    }

}
