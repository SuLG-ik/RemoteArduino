package ru.sulgik.remotearduino.modules.database.firebase

import com.google.firebase.auth.FirebaseAuth
import ru.sulgik.remotearduino.modules.database.bases.AuthController
import ru.sulgik.remotearduino.modules.database.bases.Profile
import ru.sulgik.remotearduino.modules.database.firebase.converters.AuthResultToProfile
import ru.sulgik.remotearduino.modules.tasks.MutableTask
import ru.sulgik.remotearduino.modules.tasks.Task

class FireAuth : AuthController() {

    val auth = FirebaseAuth.getInstance()

    override fun isLoggedOut(): Boolean {
        return user == null
    }

    override fun signUp(email: String, password: String): Task<Profile> {
        return MutableTask<Profile>().setAttachedTask(AuthResultToProfile(), auth.createUserWithEmailAndPassword(email, password))
    }

    override fun sighIn(email: String, password: String): Task<Profile> {
        return MutableTask<Profile>().setAttachedTask(AuthResultToProfile(), auth.signInWithEmailAndPassword(email,password))
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getUser(): Profile? {
        return FireProfile(user = (auth.currentUser ?: return null))
    }

    companion object{
    }

}