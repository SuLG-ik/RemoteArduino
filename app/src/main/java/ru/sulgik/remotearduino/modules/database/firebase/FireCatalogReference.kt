package ru.sulgik.remotearduino.modules.database.firebase

import com.google.firebase.firestore.FirebaseFirestore
import ru.sulgik.remotearduino.modules.database.bases.CatalogReference
import ru.sulgik.remotearduino.modules.database.bases.DataReference
import ru.sulgik.remotearduino.modules.database.firebase.converters.DocumentSnapshotToData
import ru.sulgik.remotearduino.modules.tasks.Task
import ru.sulgik.remotearduino.modules.tasks.toOther
import java.util.HashMap

class FireCatalogReference(private val fs : FirebaseFirestore) : CatalogReference() {
    override fun getPath(): String {
        TODO("Not yet implemented")
    }

    override fun insert(data: HashMap<String, Any>): Task<DataReference> {
        TODO("Not yet implemented")
    }

    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun data(): DataReference {
        TODO("Not yet implemented")
    }

    override fun data(path: String): DataReference {
        TODO("Not yet implemented")
    }

}