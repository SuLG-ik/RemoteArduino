package ru.sulgik.remotearduino.modules.database.exceptions

class MigrationException(from: String, to:String , type : Long) : DatabaseException("Migration exception $from to $to for $type")