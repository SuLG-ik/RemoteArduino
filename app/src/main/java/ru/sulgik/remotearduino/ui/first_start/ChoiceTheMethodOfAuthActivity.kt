package ru.sulgik.remotearduino.ui.first_start

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.AuthResult
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.bind
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.ActivityChoiceTheMethodOfAuthBinding
import ru.sulgik.remotearduino.modules.delegate.viewBindings
import ru.sulgik.remotearduino.modules.events.bindToLifecycle
import ru.sulgik.remotearduino.ui.RemoteArduinoActivity

class ChoiceTheMethodOfAuthActivity : RemoteArduinoActivity(R.layout.activity_choice_the_method_of_auth) {

    private val binding by viewBindings<ActivityChoiceTheMethodOfAuthBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "second")

        binding.btnSignIn.setOnClickListener {
            (supportFragmentManager.findFragmentByTag(TAG_REQUEST_FRAGMENT) as? BottomSheetDialogFragment)?.dismiss()
            SignInFragment().show(supportFragmentManager, TAG_REQUEST_FRAGMENT)
        }

        binding.btnSignUp.setOnClickListener {
            (supportFragmentManager.findFragmentByTag(TAG_REQUEST_FRAGMENT) as? BottomSheetDialogFragment)?.dismiss()
            SignUpFragment().show(supportFragmentManager, TAG_REQUEST_FRAGMENT)
        }

        EventBus.getDefault().bindToLifecycle(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAuth(result : AuthResult){
        finish()
    }

    override val TAG: String
        get() = "ChoiceTheMethodOfAuthActivity"

    override fun onBackPressed() {
        super.onBackPressed()
    }
    companion object{
        const val TAG_REQUEST_FRAGMENT = "request_fragment_tag"
    }
}
