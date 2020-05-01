package ru.sulgik.remotearduino.modules.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import org.apache.commons.lang3.RandomStringUtils
import ru.sulgik.remotearduino.modules.permission.ui.BaseMaterialPermissionRequestFragment
import ru.sulgik.remotearduino.modules.tasks.Listener
import ru.sulgik.remotearduino.modules.tasks.Task

class MaterialPermissionManager : PermissionManager {

    private val taskController = PermissionRequestController()
    var builder = BaseMaterialPermissionRequestFragment.Builder()

    override fun with(
        activity: FragmentActivity,
        permissions: Array<out MaterialPermission>,
        listener: Listener<Array<out MaterialPermission>>
    ): PermissionManager = with(activity ,activity, permissions, listener)

    private fun with(activity: FragmentActivity,
                     owner: LifecycleOwner,
                     permissions: Array<out MaterialPermission>,
                     listener: Listener<Array<out MaterialPermission>>
    ): MaterialPermissionManager {
        val token: String = getToken()
        taskController.generateTask(activity, token).addOnSuccessListener(owner, listener)
        if(allGranted(activity, permissions)) {
            taskController.postValue(permissions)
        }
        else{
            builder.build(permissions, token).show(activity.supportFragmentManager, FRAGMENT_TAG)
        }
        return this
    }

    override fun with(
        fragment: Fragment,
        permissions: Array<out MaterialPermission>,
        listener: Listener<Array<out MaterialPermission>>
    ): PermissionManager = with(fragment.requireActivity(), permissions, listener)

    override fun request(
        activity: FragmentActivity,
        permissions: Array<out MaterialPermission>
    ): Task<Array<out MaterialPermission>> {
        val token = getToken()
        builder.build(permissions, token).show(activity.supportFragmentManager, FRAGMENT_TAG)
        return taskController.generateTask(activity, token)
    }

    override fun request(
        fragment: Fragment,
        permissions: Array<out MaterialPermission>
    ): Task<Array<out MaterialPermission>> = request(fragment.requireActivity(), permissions)

    companion object{

        const val RECEIVER = "ru.sulgik.remotearduino.modules.permission:RECEIVER:542388"

        private const val FRAGMENT_TAG = "ru.sulgik.remotearduino.modules.permission:REQUEST_FRAGMENT:465178"
        const val TAG = "PermissionManager"

        fun getToken() : String = RandomStringUtils.random(16,true,true)
        fun allGranted(context: Context, permissions: Array<out MaterialPermission>): Boolean {
            permissions.forEach {
                if(ContextCompat.checkSelfPermission(context, it.path) == PackageManager.PERMISSION_DENIED)
                    return false
            }
            return true
        }
    }



}