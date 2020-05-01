package ru.sulgik.remotearduino.modules.database.live

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun <T> CollectionReference.flow(clazz: Class<T>) : Flow<List<T>> = callbackFlow {
    val reg = this@flow.addSnapshotListener { query, e ->

        if(e == null){
            val list = mutableListOf<T>()
            query?.documents?.forEach {
                list.add(it.toObject(clazz)!!)
            }
            offer(list)
        }

    }
    awaitClose {
        cancel()
        reg.remove()
    }
}

@ExperimentalCoroutinesApi
fun <T> DocumentReference.flow(clazz: Class<T>) : Flow<T> = callbackFlow {
    val reg = this@flow.addSnapshotListener { doc, e ->

        if(e == null){
            if(doc != null){
                offer(doc.toObject(clazz)!!)
            }
        }

    }
    awaitClose {
        cancel()
        reg.remove()
    }
}