package ru.sulgik.remotearduino.modules.database.bases

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import ru.sulgik.remotearduino.modules.tasks.Listener
import ru.sulgik.remotearduino.modules.tasks.Task

interface Profile{

    val name : String?
    val email : String?
    val uid : String
    val authKey : String

    val picture : Uri?

    val isRemote : Boolean
    val isAnonymous : Boolean

    fun updateEmail(email : String) : Task<Void>

    fun updatePassword(password : String) : Task<Void>

    fun updatePicture(picture: Uri) : Task<Void>

    fun updateName(name : String) : Task<Void>

}