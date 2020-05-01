package ru.sulgik.remotearduino.koin

import android.provider.ContactsContract
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.sulgik.remotearduino.modules.bluetooth.BluetoothManager
import ru.sulgik.remotearduino.modules.database.MainDatabases
import ru.sulgik.remotearduino.modules.database.bases.Database
import ru.sulgik.remotearduino.modules.database.bases.DatabasesController
import ru.sulgik.remotearduino.modules.database.firebase.FireDB
import ru.sulgik.remotearduino.modules.permission.MaterialPermissionManager

val globalModule = module {
    single { MaterialPermissionManager() }
}

val connectivity = module{
    single{BluetoothManager()}
}

val database = module{
    single <DatabasesController>{ MainDatabases() }
}