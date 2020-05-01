package ru.sulgik.remotearduino.modules.database.migration

import ru.sulgik.remotearduino.modules.database.RemoteDevice
import ru.sulgik.remotearduino.modules.database.exceptions.MigrationException
import java.lang.Exception

interface Migration {

    val from : Long

    val to : Long

    @Throws(MigrationException::class)
    fun migrate(map: HashMap<Any, Any?>) : HashMap<Any, Any?>

    class From1to2 : Migration{
        override val from: Long = 1
        override val to: Long = 2

        override fun migrate(map: HashMap<Any, Any?>): HashMap<Any, Any?> {
            try {
                val m = map[RemoteDevice.EXTRA_ID] as Long
                map[RemoteDevice.EXTRA_PICTURE_URI] = m + 10
                return map
            }catch (e : Exception){
                throw MigrationException(from.toString(), to.toString(), 0)
            }
        }

    }

}