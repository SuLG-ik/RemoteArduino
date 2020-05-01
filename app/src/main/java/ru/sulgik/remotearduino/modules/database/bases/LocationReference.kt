package ru.sulgik.remotearduino.modules.database.bases

import com.google.firebase.firestore.CollectionReference
import org.w3c.dom.Document
import ru.sulgik.remotearduino.modules.tasks.Task


interface LocationReference {

    val isDocument : Boolean

    val path : String

    fun getLocation(path : String) : CollectionReference

    fun insertData(data : HashMap<Any, Any>) : Task<Void>

    fun insertData(path : String, data: HashMap<Any, Any>) : Task<Void>

}