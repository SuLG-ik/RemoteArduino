package ru.sulgik.remotearduino.modules.bluetooth

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.Exception

@Parcelize
class BluetoothDataToSend(val targetDevice : BluetoothDevice, val data: String) : Parcelable {

    companion object{
        const val EXTRA: String = "ru.sulgik.remotearduino.modules.bluetooth:EXTRA:597895"

        @Throws(IllegalArgumentException::class)
        fun fromBundle(bundle: Bundle?) = try {
            bundle!!.getParcelable<BluetoothDataToSend>(EXTRA)!!
        }catch (e: Exception){
            throw IllegalArgumentException("Invalid bundle. parcelable cant be obtained")
        }

    }


}