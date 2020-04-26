package ru.sulgik.remotearduino.permission.def

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment
import ru.sulgik.remotearduino.permission.MaterialPermission
import ru.sulgik.remotearduino.permission.MaterialPermission.Companion.IMPORTANT
import ru.sulgik.remotearduino.permission.MaterialPermissionManager
import ru.sulgik.remotearduino.permission.rebuild

class DefaultPermissionRequestFragment  : BaseMaterialPermissionRequestFragment() {


    lateinit var confirm : MaterialButton
    var onlySelected = false
        set(value) {
            if (value){
                confirm.text = "Permit selected"
            }else{
                confirm.text = "Permit all"
            }
            field = value
        }

    val adapter = DefaultListAdapter(object : DefaultListAdapter.OnSelectItem {
        override fun onSelect(selected: Array<MaterialPermission>) {
            onlySelected = selected.isNotEmpty()
        }
    })

    override fun onRequest(permissions: Array<MaterialPermission>) {
        if(permissions.all { it.state == PackageManager.PERMISSION_GRANTED }){
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        sendCallback(permissions)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.content_permission, container, false)
        val linearLayout = root.findViewById<LinearLayoutCompat>(R.id.main_layout)

        confirm = root.findViewById(R.id.confirm_button)

        val list = root.findViewById<RecyclerView>(R.id.permissions_list)

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)

        adapter.permissions = permissions

        confirm.setOnClickListener {
            request(if (!onlySelected) permissions else adapter.getSelected())
        }

        return root
    }

}