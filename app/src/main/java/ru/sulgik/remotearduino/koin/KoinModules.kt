package ru.sulgik.remotearduino.koin

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.sulgik.remotearduino.modules.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.modules.database.devices.DevicesRepository
import ru.sulgik.remotearduino.modules.database.devices.DevicesViewModel
import ru.sulgik.remotearduino.modules.database.devices.IDevicesRepository
import ru.sulgik.remotearduino.modules.database.devices.IDevicesViewModel
import ru.sulgik.remotearduino.modules.permission.MaterialPermissionManager

val globalModule = module {
    single { MaterialPermissionManager() }
}

val connectivity = module{
    single{BluetoothManager()}
}

val database = module{
    factory <IDevicesRepository> { DevicesRepository() }
    viewModel{ DevicesViewModel(androidApplication(), get()) }

}