package ru.sulgik.remotearduino.modules.bluetooth.requestdevice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.DeviceItemBinding


class DevicesAdapter : ListAdapter<DeviceToShowItem, DevicesAdapter.ViewHolder>(callBack) {

    interface OnDeviceClickListener {
        fun onClick(position: Int, device : DeviceToShowItem, view: View)
    }
    var onDeviceClickListener : OnDeviceClickListener = object : OnDeviceClickListener{
        override fun onClick(position: Int, device: DeviceToShowItem, view: View) {

        }
    }
    fun setOnDeviceClickListener(listener : (position: Int, device : DeviceToShowItem, view : View) -> Unit) {
        onDeviceClickListener = object : OnDeviceClickListener{
            override fun onClick(position: Int, device: DeviceToShowItem, view: View) {
                listener.invoke(position, device, view)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<DeviceItemBinding>(view)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(DataBindingUtil.inflate<DeviceItemBinding>(LayoutInflater.from(parent.context), R.layout.device_item, parent, false).root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onDeviceClickListener.onClick(position, currentList[position], holder.binding.name)
        }
    }

    fun fillDown(){
        currentList.clear()
    }

    fun addItems(items : Collection<DeviceToShowItem>){
        currentList.addAll(items)
    }

    companion object{

        val callBack = object : DiffUtil.ItemCallback<DeviceToShowItem>(){
            override fun areItemsTheSame(oldItem: DeviceToShowItem, newItem: DeviceToShowItem): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: DeviceToShowItem, newItem: DeviceToShowItem): Boolean =
                (oldItem.name == newItem.name
                        && oldItem.mac == newItem.mac)

        }


    }

}