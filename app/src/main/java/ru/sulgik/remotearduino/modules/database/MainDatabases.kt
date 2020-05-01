package ru.sulgik.remotearduino.modules.database

import ru.sulgik.remotearduino.modules.database.bases.Database
import ru.sulgik.remotearduino.modules.database.bases.DatabasesController
import ru.sulgik.remotearduino.modules.database.firebase.FireDB

class MainDatabases (

) : DatabasesController {

    private val remoteDatabase: Database = FireDB()

    override fun getRemoteDatabase(): Database {
        return remoteDatabase
    }

    override fun getLocalDatabase(): Database {
        return remoteDatabase
    }

}