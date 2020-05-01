package ru.sulgik.remotearduino.modules.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.util.Util
import com.google.firebase.ktx.Firebase
import java.security.SecureRandom

object GeneralRepository{

    val rand = SecureRandom()

    const val LOCATION_USERS = "users/"

    val uid get() = (FirebaseAuth.getInstance().currentUser?.uid ?: "1only_read_repository") + "/"

    val userLocation = Firebase.firestore.collection(LOCATION_USERS).document(uid)


    fun autoId(): String {
        val builder = StringBuilder()
        val maxRandom = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length

        for (i in 0..19) {
            builder.append(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"[rand.nextInt(maxRandom)]
            )
        }

        return builder.toString()
    }
}