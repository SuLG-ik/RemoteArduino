package ru.sulgik.remotearduino.permission

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment.Builder.Companion.EXTRA_PERMISIONS
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment.Builder.Companion.EXTRA_TOKEN
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment.Builder.Companion.TOKEN_NONE
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment.Builder.Companion.buildIntent
import ru.sulgik.remotearduino.permission.MaterialPermissionManager.Companion.RECEIVER
import ru.sulgik.remotearduino.permission.def.DefaultPermissionRequestFragment

abstract class BaseMaterialPermissionRequestFragment : BottomSheetDialogFragment() {

    protected var permissions : Array<MaterialPermission> = emptyArray()
        get() = field.rebuild(requireContext())
    private lateinit var token: String
    abstract fun onRequest(permissions: Array<MaterialPermission>)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        permissions = requireArguments().getParcelableArray(EXTRA_PERMISIONS)!!.filterIsInstance<MaterialPermission>()
            .toTypedArray()
        token = requireArguments().getString(EXTRA_TOKEN, TOKEN_NONE)
    }

    @CallSuper
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST){
            onRequest(this.permissions)
        }
    }

    fun request(permissions : Array<MaterialPermission>){
        requestPermissions(permissions.asPathArray(), PERMISSION_REQUEST)
    }

    fun sendCallback(permissions: Array<MaterialPermission>){
        Log.d("BaseMaterial", "sendCallback")
        activity?.sendBroadcast(buildIntent(Intent(RECEIVER), permissions, token))
    }


    companion object{
        const val TAG = "ru.sulgik.remotearduino.permission:BaseMaterialPermissionRequestFragment:456648"
        const val PERMISSION_REQUEST = 89
    }

    class Builder (){

        private var fragment : Class<out BaseMaterialPermissionRequestFragment> = DefaultPermissionRequestFragment::class.java
        fun setFragment(fragment: Class<out BaseMaterialPermissionRequestFragment>): Builder = this.apply {
            this. fragment = fragment
        }
        var config : PermissionRequestConfig = PermissionRequestConfig.build()

        fun setConfig(conf : PermissionRequestConfig): Builder {
            config = conf
            return this
        }
        fun setConfig(conf: PermissionRequestConfig.() -> Unit): Builder {
            config = PermissionRequestConfig.build(conf)
            return this
        }

        fun build(permissions: Array<out MaterialPermission>, token : String): BaseMaterialPermissionRequestFragment = fragment.newInstance().apply {
                arguments = Bundle().apply {
                    putParcelableArray(EXTRA_PERMISIONS, permissions)
                    putString(EXTRA_TOKEN, token)
                }
                if (config.isBackgroundTransparent)
                    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
            }

        companion object {

            const val TOKEN_NONE = "NONE"
            const val EXTRA_PERMISIONS = "TokenIntoFragment:45656"
            const val EXTRA_TOKEN = "TikenExtra:222"

            fun buildIntent(intent: Intent, permissions : Array<MaterialPermission>, token: String) = intent.putExtra(EXTRA_PERMISIONS, permissions).putExtra(EXTRA_TOKEN, token)
        }
    }

}