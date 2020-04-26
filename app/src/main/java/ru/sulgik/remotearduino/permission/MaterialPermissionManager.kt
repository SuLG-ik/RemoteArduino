package ru.sulgik.remotearduino.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.apache.commons.lang3.RandomStringUtils
import ru.sulgik.remotearduino.tasks.Listener
import ru.sulgik.remotearduino.tasks.Task
import ru.sulgik.remotearduino.tasks.TaskController

class MaterialPermissionManager : PermissionManager {

    private val taskController = PermissionRequestController()
    private var builder = BaseMaterialPermissionRequestFragment.Builder()

    override fun with(
        activity: FragmentActivity,
        permissions: Array<out MaterialPermission>,
        listener: Listener<Array<out MaterialPermission>>
    ): PermissionManager {
        val token: String = getToken()
        taskController.generateTask(activity, token).addOnSuccessListener(listener)
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

        const val RECEIVER = "ru.sulgik.remotearduino.permission:RECEIVER:542388"

        private const val FRAGMENT_TAG = "ru.sulgik.remotearduino.permission:REQUEST_FRAGMENT:465178"
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