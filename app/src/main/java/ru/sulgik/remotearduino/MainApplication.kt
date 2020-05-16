package ru.sulgik.remotearduino

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.sulgik.remotearduino.koin.auth
import ru.sulgik.remotearduino.koin.connectivity
import ru.sulgik.remotearduino.koin.database
import ru.sulgik.remotearduino.koin.permissions

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(
                permissions,
                database,
                auth,
                connectivity
            )
        }
    }
}