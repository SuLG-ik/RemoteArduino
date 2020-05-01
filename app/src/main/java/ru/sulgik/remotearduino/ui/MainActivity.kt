package ru.sulgik.remotearduino.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.modules.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.modules.database.devices.DevicesViewModel
import ru.sulgik.remotearduino.modules.database.devices.IDevicesViewModel
import ru.sulgik.remotearduino.modules.database.pojo.RemoteDevice
import ru.sulgik.remotearduino.modules.log.LogService
import ru.sulgik.remotearduino.modules.log.SystemLog
import ru.sulgik.remotearduino.modules.permission.PermissionManager
import ru.sulgik.remotearduino.modules.tasks.Listener

class MainActivity : RemoteArduinoActivity(
    R.layout.activity_main
){

    val bluetooth : BluetoothManager by inject()
    val permissionManager : PermissionManager by inject()

    val devices : IDevicesViewModel by viewModel<DevicesViewModel>()

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this,
            R.id.fragmentNav
        )
        NavigationUI.setupWithNavController(bottomNavigation, navController)

    }

    private fun userCheck(){
//        if(remote.user == null && !Alerter.isShowing){
//            notifyLayout.show(NotifyTask.create {
//                text = "Ti lox text"
//                title = "sosi"
//                image = getDrawable(R.drawable.ic_google)
//                Button("Text", R.style.AppButton){
//                    Snackbar.make(bottomNavigation, "Lox", Snackbar.LENGTH_SHORT)
//                }
//            })
//        }
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
