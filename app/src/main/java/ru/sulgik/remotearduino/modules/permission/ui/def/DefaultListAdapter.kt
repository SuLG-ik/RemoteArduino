package ru.sulgik.remotearduino.modules.permission.ui.def

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.modules.permission.MaterialPermission

class DefaultListAdapter (val listener : OnSelectItem) : RecyclerView.Adapter<DefaultListAdapter.VH>() {

    interface OnSelectItem {
        fun onSelect(selected : Array<MaterialPermission>)
    }

    var permissions : Array<MaterialPermission> = emptyArray()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.permission_item, parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(permissions[position], position)
    }

    override fun getItemCount(): Int = permissions.size

    fun getSelected()= permissions.filter{ it.isSelected}.toTypedArray()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo = itemView.findViewById<AppCompatImageView>(R.id.data_logo)
        val title = itemView.findViewById<AppCompatTextView>(R.id.data_title)
        val description = itemView.findViewById<AppCompatTextView>(R.id.data_description)
        val state = itemView.findViewById<AppCompatImageView>(R.id.data_state)

        fun bind(permission: MaterialPermission, position: Int){
            state.setImageResource(getStateDrawable(permission))
            logo.setImageResource(if (permission.state == PackageManager.PERMISSION_GRANTED) permission.logo else permission.logo2)
            title.text = permission.name
            description.text = permission.description
            if(permission.description.isNotEmpty()) description.visibility = View.VISIBLE
            itemView.setOnClickListener {
                onSelect(permission, position)
            }
        }

        private fun onSelect(permission: MaterialPermission, position :Int) {
            if (permission.state == PackageManager.PERMISSION_DENIED){
                permission.inverseSelectable()
                permissions[position].isSelected = permission.isSelected
                state.setImageResource(getStateDrawable(permission))
                listener.onSelect(getSelected())
            }else{
                onGranted(permission, position)
            }
        }

        private fun onGranted(permission: MaterialPermission, position: Int) {

        }


    }

    companion object {
        fun getStateDrawable(permission: MaterialPermission) = getStateDrawable(permission.state, permission.isSelected)
        fun getStateDrawable(state : Int, selected: Boolean): Int {
            return if(state == PackageManager.PERMISSION_DENIED){
                if(selected) {
                    R.drawable.checked
                }
                else {
                    R.drawable.gray_circle
                }
            }else{
                R.drawable.blue_circle
            }
        }
    }

}