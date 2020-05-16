package ru.sulgik.remotearduino.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.modules.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.modules.log.LogService
import ru.sulgik.remotearduino.modules.log.SystemLog
import ru.sulgik.remotearduino.modules.permission.PermissionManager

class MainActivity : RemoteArduinoActivity(
    R.layout.activity_main
){

    private val bluetooth : BluetoothManager by inject()
    private val permissionManager : PermissionManager by inject()

    private val navController: NavController by lazy (LazyThreadSafetyMode.NONE) {
        Navigation.findNavController(this, R.id.fragmentNav)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NavigationUI.setupWithNavController(findViewById<BottomNavigationView>(R.id.bottomNavigation), navController)
    }

    override val TAG: String
        get() = "MainActivity"

    companion object{
        val log: LogService = SystemLog("MainActivityLogger")
        const val MAIN_ACTIVITY_TAG = "MainActivity:456789"
    }

}
