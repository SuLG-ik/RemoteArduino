package ru.sulgik.remotearduino.modules.database.firebase.converters

import com.google.firebase.firestore.DocumentSnapshot
import org.w3c.dom.Document
import ru.sulgik.remotearduino.modules.tasks.Converter
import java.lang.Exception
import kotlin.concurrent.thread

class DocumentSnapshotToData : Converter<DocumentSnapshot, HashMap<String, Any?>>{

    override fun convertForward(value: DocumentSnapshot): HashMap<String, Any?> {
        return HashMap(value.data ?: mapOf())
    }

    override fun convertBackward(value: HashMap<String, Any?>): DocumentSnapshot {
        throw Exception("Use other way to insert data")
    }

}
