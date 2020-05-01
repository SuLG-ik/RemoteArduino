package ru.sulgik.remotearduino.modules.database.firebase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.sulgik.remotearduino.modules.database.RemoteDevice
import ru.sulgik.remotearduino.modules.database.bases.AuthController
import ru.sulgik.remotearduino.modules.database.bases.Database

class FireDevicesLiveData private constructor(private val auth : AuthController) : MutableLiveData<List<RemoteDevice>>() {

    private val firestore = Firebase.firestore

    override fun onActive() {
        firestore.collection(FireStorage.LOCATION_USERS+ "/" + (auth.user?.authKey ?: return))
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.documents?.forEach {

                }
        }
    }

    fun getDevices() {

    }

    companion object{
        @Volatile
        private var instance : LiveData<List<RemoteDevice>>? = null

        fun getInstance(auth: AuthController): LiveData<List<RemoteDevice>> {
            return instance ?: synchronized(this){
                val inst = FireDevicesLiveData(auth)
                instance = inst
                return inst
            }
        }
    }

}