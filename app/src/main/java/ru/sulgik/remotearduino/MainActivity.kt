package ru.sulgik.remotearduino

import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_main.*
import ru.sulgik.remotearduino.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.log.LogService
import ru.sulgik.remotearduino.log.SystemLog
import ru.sulgik.remotearduino.notify.Notifier
import ru.sulgik.remotearduino.permission.MaterialPermission
import ru.sulgik.remotearduino.permission.MaterialPermissionManager
import ru.sulgik.remotearduino.tasks.Listener
import ru.sulgik.remotearduino.tasks.MutableTask

class MainActivity : RemoteArduinoActivity(R.layout.activity_main){

    val bluetooth = BluetoothManager()
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.fragmentNav)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        val permissionManager = MaterialPermissionManager()

        permissionManager.with(this, getBluetoothDiscoveringPermissions(this)){
            log.debug(it.joinToString { "${it.name} - ${it.state == PackageManager.PERMISSION_GRANTED}" })
        }

        userCheck()
    }

    private fun userCheck(){
        if(db.userIsEmpty && !Alerter.isShowing){
            Notifier.create(this, R.id.notifyLayout)
                .setText("Tap here")
                .setTitle("Please, sign in").setOnClickListener(
                View.OnClickListener {
                    navController.navigate(R.id.accountFragment)
                    Notifier.hide()
                }
                )
                .show()
        }
    }

    override val TAG: String
        get() = "MainActivity"

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object{
        val log: LogService = SystemLog("MainActivityLogger")
        const val MAIN_ACTIVITY_TAG = "MainActivity:456789"
    }

}
