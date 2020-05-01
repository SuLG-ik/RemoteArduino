package ru.sulgik.remotearduino.modules.database.live

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception

fun <T> DocumentReference.livedata(clazz: Class<T>) : LiveData<Result<T>> = DocumentLiveData(this, clazz)

fun <T> CollectionReference.livedata(clazz: Class<T>) : LiveData<List<T>> = CollectionLiveData(this, clazz)


class DocumentLiveData<T>(private val document: DocumentReference, private val clazz: Class<T>) : LiveData<Result<T>>() {

    private val handler = Handler()
    private var reg : ListenerRegistration? = null
    private val remover = Runnable {
        reg?.remove()
        reg = null
    }

    override fun onActive() {
        super.onActive()
        reg = document.addSnapshotListener{it, e ->
            if(e == null){
                if (it == null){
                    postValue(Result.failure(Exception("There is no any objects")))
                }else{
                    try {
                        postValue(Result.success(it.toObject(clazz)!!))
                    }catch (e:Exception){
                        postValue(Result.failure(Exception("Can't to transform received data")))
                    }
                }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        handler.postDelayed(remover,0)
    }

}

class CollectionLiveData<T>(private val collection: CollectionReference, private val clazz: Class<T>) : LiveData<List<T>>() {

    private val handler = Handler()
    private var reg : ListenerRegistration? = null
    private val remover = Runnable {
        reg?.remove()
        reg = null
    }

    override fun onActive() {
        super.onActive()
        reg = collection.addSnapshotListener{it, e ->
            if(e == null){
                val list = mutableListOf<T>()
                it?.documents?.forEach {
                    try {
                        list.add(it.toObject(clazz)!!)
                    }catch (e : Exception){

                    }
                }
                postValue(list)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        handler.postDelayed(remover,0)
    }

}

