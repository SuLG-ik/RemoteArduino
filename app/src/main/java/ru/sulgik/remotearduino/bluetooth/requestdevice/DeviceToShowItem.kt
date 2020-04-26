package ru.sulgik.remotearduino.bluetooth.requestdevice

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.sulgik.remotearduino.R

@Parcelize
class DeviceToShowItem(val device: BluetoothDevice, val deviceClass : Int = NONE) : Parcelable{

    val name : String = device.name
    val mac : String = device.address

    fun getState(context: Context) = when(device.bondState){
        BluetoothDevice.BOND_BONDED -> context.getString(R.string.bounded)
        BluetoothDevice.BOND_BONDING -> context.getString(R.string.bounding)
        BluetoothDevice.BOND_NONE -> context.getString(R.string.unrelated)
        else -> {context.getString(R.string.unknown)}
    }

    fun getLogoDraw(context: Context) : Drawable = when(deviceClass) {
        ARDUINO -> {
            context.getDrawable(R.drawable.ic_developer_board)!!
        }
        else -> {
            when (device.bondState) {
                BluetoothDevice.BOND_BONDED -> context.getDrawable(R.drawable.ic_bluetooth_bounded)!!
                BluetoothDevice.BOND_BONDING -> context.getDrawable(R.drawable.ic_bluetooth_bounding)!!
                BluetoothDevice.BOND_NONE -> context.getDrawable(R.drawable.ic_bluetooth_none)!!
                else -> {
                    context.getDrawable(R.drawable.gray_circle)!!
                }
            }
        }
    }

    fun getStateDraw(context: Context) : Drawable =  when (device.bondState) {
        BluetoothDevice.BOND_BONDED -> context.getDrawable(R.drawable.blue_circle)!!
        BluetoothDevice.BOND_BONDING -> context.getDrawable(R.drawable.orange_circle)!!
        BluetoothDevice.BOND_NONE -> context.getDrawable(R.drawable.gray_circle)!!
        else -> {
            context.getDrawable(R.drawable.gray_circle)!!
        }
    }


    companion object{
        const val NONE = 0
        const val ARDUINO = 1

        fun sortList(devices : Collection<DeviceToShowItem>): List<DeviceToShowItem> = devices.toSet().toList().sortedBy { it.name }

    }

}