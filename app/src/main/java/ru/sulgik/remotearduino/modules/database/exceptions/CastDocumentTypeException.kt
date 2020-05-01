package ru.sulgik.remotearduino.modules.database.exceptions

class CastDocumentTypeException(type : String) : DatabaseException("Cast exception. Document hasn't needed parameters like $type")