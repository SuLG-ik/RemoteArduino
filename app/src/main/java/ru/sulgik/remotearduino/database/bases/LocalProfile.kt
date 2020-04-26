package ru.sulgik.remotearduino.database.bases

interface LocalProfile {

    val name : String?
    val email : String?
    val uid : String
    val authKey : String

    fun updateProfile(updater : Updater)

    interface Updater {
        fun update()
    }
}