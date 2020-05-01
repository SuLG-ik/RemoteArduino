package ru.sulgik.remotearduino.modules.database.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.sulgik.remotearduino.modules.database.RemoteDevice
import ru.sulgik.remotearduino.modules.database.bases.LocationReference
import ru.sulgik.remotearduino.modules.database.bases.Profile
import ru.sulgik.remotearduino.modules.database.bases.StorageController

class FireStorage(private val user : Profile?) : StorageController() {

    val firestore = Firebase.firestore

    override fun getAllDevices(): LiveData<RemoteDevice> {
        TODO()
    }


    companion object{
        const val LOCATION_USERS = "/users"
    }

}