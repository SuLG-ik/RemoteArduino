package ru.sulgik.remotearduino.modules.authentication

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.KoinComponent

class AuthService : AuthKeyOwner{

    override val key: String get() = (auth.currentUser?.uid ?: "1only_read_repository") + "/"

    val auth  :FirebaseAuth = Firebase.auth

    val user = auth.currentUser

    fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun signOut() {
        auth.signOut()
    }

    fun updateDisplayedName(name : String) : Task<Void>? {
        return auth.currentUser?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
    }

    fun updateProfileURI(uri : Uri) : Task<Void>? {
        return auth.currentUser?.updateProfile(UserProfileChangeRequest.Builder().setPhotoUri(uri).build())
    }
    fun updateEmail(email : String): Task<Void>? {
        return auth.currentUser?.updateEmail(email)
    }

    fun updatePassword(password : String): Task<Void>? {
        return auth.currentUser?.updatePassword(password)
    }

    fun reload(): Task<Void>? {
        return auth.currentUser?.reload()
    }

}