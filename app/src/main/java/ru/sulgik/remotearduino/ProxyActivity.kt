package ru.sulgik.remotearduino

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import org.koin.android.ext.android.inject
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.ui.MainActivity
import ru.sulgik.remotearduino.ui.RemoteArduinoActivity
import ru.sulgik.remotearduino.ui.first_start.ChoiceTheMethodOfAuthActivity
import ru.sulgik.remotearduino.ui.first_start.FirstStartActivity

class ProxyActivity : RemoteArduinoActivity(R.layout.activity_proxy) {
    override val TAG: String
        get() = "ProxyActivity"

    private val auth by inject<AuthService>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT){
            checkAll()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")
        checkAll()

    }

    private fun checkInfoActivity(){
        Log.d(TAG, "checkInfoActivity")
        startActivityForResult(Intent(this, FirstStartActivity::class.java), RESULT)
    }

    private fun checkAuth(){
        Log.d(TAG, "checkAuth")
        startActivityForResult(Intent(this, ChoiceTheMethodOfAuthActivity::class.java),RESULT)
    }

    private fun startApplication(){
        Log.d(TAG, "startApplication)")
        startActivityForResult(Intent(this, MainActivity::class.java), RESULT)
    }

    private fun checkAll(){
        if (firstStartIsNotCompleted()){
            checkInfoActivity()
        }else if (auth.user == null){
            checkAuth()
        }else if (!firstStartIsNotCompleted() && auth.user != null){
            startApplication()
            finish()
        }else{
            checkAll()
        }
    }

    private fun firstStartIsNotCompleted(): Boolean {
        return sharedPreferences.getBoolean(FirstStartActivity.IS_NOT_COMPLETED, true)
    }

    companion object{
        const val RESULT = 23
    }

}
