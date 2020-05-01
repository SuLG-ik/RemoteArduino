package ru.sulgik.remotearduino.modules.database.firebase

import ru.sulgik.remotearduino.modules.database.bases.AuthController
import ru.sulgik.remotearduino.modules.database.bases.Database
import ru.sulgik.remotearduino.modules.database.bases.Profile
import ru.sulgik.remotearduino.modules.database.bases.StorageController

class FireDB : Database() {

    private val auth = FireAuth()
    private val storage = FireStorage(auth.user)
    
    override fun isRemote(): Boolean {
        return true
    }

    override fun getUser(): Profile? {
        return auth.user
    }

    override fun getAuthController(): AuthController {
        return auth
    }

    override fun getStorageController(): StorageController {
        return storage
    }

}