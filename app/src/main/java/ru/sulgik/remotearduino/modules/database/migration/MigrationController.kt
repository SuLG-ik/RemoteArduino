package ru.sulgik.remotearduino.modules.database.migration

import ru.sulgik.remotearduino.modules.database.RemoteDevice
import ru.sulgik.remotearduino.modules.database.exceptions.MigrationException

class MigrationController {

    private val migrations : Map<Long, Migration> = arrayOf(
        Migration.From1to2()
    ).associateBy { it.from }

    fun migrate(map: HashMap<Any, Any?>) : HashMap<Any, Any?>{
        var version = map[RemoteDevice.EXTRA_VERSION] as Long
        while (version < RemoteDevice.LAST_VERSION){
            val mig = migrations[version] ?: throw MigrationException(version.toString(), (version +1).toString(), 0)
            mig.migrate(map)
        }
        return map
    }

}