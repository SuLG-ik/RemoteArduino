package ru.sulgik.remotearduino.modules.database.exceptions

import java.io.IOException

open class DatabaseException(message : String = "DatabaseError") : IOException(message) {
    override fun toString(): String {
        return message ?: ""
    }
}
