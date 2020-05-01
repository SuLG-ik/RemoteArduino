package ru.sulgik.remotearduino.modules.database.firebase.converters

import com.google.firebase.auth.AuthResult
import ru.sulgik.remotearduino.modules.database.bases.Profile
import ru.sulgik.remotearduino.modules.database.firebase.FireProfile
import ru.sulgik.remotearduino.modules.tasks.Converter
import java.lang.Exception

class AuthResultToProfile : Converter<AuthResult, Profile> {

    override fun convertForward(value: AuthResult): Profile {
        return FireProfile(value.user!!)
    }

    override fun convertBackward(value: Profile): AuthResult {
        throw Exception("From profile to authresult is no available")
    }

}