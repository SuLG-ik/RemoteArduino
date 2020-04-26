package ru.sulgik.remotearduino.permission

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment.Builder.Companion.EXTRA_PERMISIONS
import ru.sulgik.remotearduino.permission.BaseMaterialPermissionRequestFragment.Builder.Companion.EXTRA_TOKEN
import ru.sulgik.remotearduino.tasks.MutableTask
import ru.sulgik.remotearduino.tasks.Task
import ru.sulgik.remotearduino.tasks.TaskController
import java.lang.Exception

class PermissionRequestController : TaskController<Array<out MaterialPermission>>() {

    var token : String? = null
    private val filter = IntentFilter(MaterialPermissionManager.RECEIVER)
    private val receiver : BroadcastReceiver = object: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(MaterialPermissionManager.TAG, "onReceive ${intent?.action}")

            if(intent?.action == MaterialPermissionManager.RECEIVER){
                val mtoken = intent.getStringExtra(EXTRA_TOKEN)

                if ( mtoken != null && token == token ) {
                    val permissions = intent.getParcelableArrayExtra(EXTRA_PERMISIONS)?.filterIsInstance<MaterialPermission>()?.toTypedArray()

                    if(permissions != null)
                        if(permissions.all{it.state == PackageManager.PERMISSION_GRANTED}) {
                            postValue(permissions)
                        } else {
                            postException(Exception("Not all permissions granted"))
                        }
                    else
                        postException(Exception("Invalid array"))
                } else {
                    postException(Exception("Invalid token"))
                }
            }
        }
    }

    private fun registerReceiver(activity: FragmentActivity){
        activity.lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun bind(){
                activity.registerReceiver(receiver, filter)
                Log.d(MaterialPermissionManager.TAG, "bind")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun unbind(){
                activity.unregisterReceiver(receiver)
                Log.d(MaterialPermissionManager.TAG, "unbind")
            }

        })
    }

    fun generateTask(activity: FragmentActivity, token : String): Task<Array<out MaterialPermission>> {
        this.token = token
        registerReceiver(activity)
        return newTask()
    }

}