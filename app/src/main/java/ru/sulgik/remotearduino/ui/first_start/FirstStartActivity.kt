package ru.sulgik.remotearduino.ui.first_start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import org.koin.android.ext.android.inject
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.ActivityFirstStartBinding
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.modules.delegate.viewBindings
import ru.sulgik.remotearduino.ui.RemoteArduinoActivity

class FirstStartActivity : RemoteArduinoActivity(R.layout.activity_first_start) {

    override val TAG: String
        get() = "FirstActivity"

    private val binding by viewBindings<ActivityFirstStartBinding>()

    private val auth by inject<AuthService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "first")

        binding.btnStart.setOnClickListener {
            changeFlag(false)

            if(auth.user == null){
                startActivity(Intent(this, ChoiceTheMethodOfAuthActivity::class.java))
            }
            finish()
        }
    }

    private fun changeFlag(value : Boolean){
        sharedPreferences.edit{
            putBoolean(IS_NOT_COMPLETED, value)
        }
    }

    override fun onBackPressed() {}

    companion object{
        const val IS_NOT_COMPLETED = "first_start_is_not"
    }

}
