package ru.sulgik.remotearduino.modules.database.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import ru.sulgik.remotearduino.modules.database.bases.Profile
import ru.sulgik.remotearduino.modules.tasks.MutableTask
import ru.sulgik.remotearduino.modules.tasks.Task
import ru.sulgik.remotearduino.modules.tasks.toOther

class FireProfile(val user: FirebaseUser) :
    Profile {

    override val name: String?
        get() = user.displayName

    override val email: String?
        get() = user.email

    override val uid: String
        get() = user.uid

    override val authKey: String
        get() = uid
    override val picture: Uri?
        get() = user.photoUrl

    override val isRemote: Boolean = true

    override val isAnonymous: Boolean get()= user.isAnonymous

    override fun updateEmail(email: String): Task<Void> {
        return user.updateEmail(email).toOther()
    }

    override fun updatePassword(password: String): Task<Void> {
        return user.updatePassword(password).toOther()
    }


    override fun updatePicture(picture: Uri): Task<Void> {
        return  user.updateProfile(UserProfileChangeRequest.Builder().setPhotoUri(picture).build()).toOther()
    }

    override fun updateName(name: String): Task<Void> {
        return user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build()).toOther()
    }


}