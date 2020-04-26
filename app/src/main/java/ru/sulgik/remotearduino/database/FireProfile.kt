package ru.sulgik.remotearduino.database

import com.google.firebase.auth.FirebaseUser
import ru.sulgik.remotearduino.database.bases.LocalProfile

class FireProfile(val user: FirebaseUser) :
    LocalProfile {

    override val name: String?
        get() = user.displayName

    override val email: String?
        get() = user.email

    override val uid: String
        get() = user.uid

    override val authKey: String
        get() = uid

    override fun updateProfile(updater: LocalProfile.Updater) {

    }


    class FireProfileUpdater{
        fun updatePassword(){

        }
    }

}