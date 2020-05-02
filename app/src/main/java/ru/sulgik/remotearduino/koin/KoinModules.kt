package ru.sulgik.remotearduino.koin

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.modules.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.modules.database.devices.DevicesRepository
import ru.sulgik.remotearduino.modules.database.devices.DevicesViewModel
import ru.sulgik.remotearduino.modules.permission.MaterialPermissionManager

val globalModule = module {
    single { MaterialPermissionManager() }
}

val connectivity = module{
    single{BluetoothManager()}
}

val auth = module {
    single { AuthService() }
}

val database = module{
    viewModel {
        DevicesViewModel(
            androidApplication(),
            DevicesRepository(get<AuthService>())
        )
    }
}