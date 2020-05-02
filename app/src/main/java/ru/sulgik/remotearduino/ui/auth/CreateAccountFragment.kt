package ru.sulgik.remotearduino.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentCreateAccountBinding
import ru.sulgik.remotearduino.modules.delegate.viewBindings
import ru.sulgik.remotearduino.modules.events.AuthResult

class CreateAccountFragment : Fragment(R.layout.fragment_create_account) {

    val binding by viewBindings { FragmentCreateAccountBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val controller = Navigation.findNavController(view)
        binding.btnSignIn.setOnClickListener {

        }

        binding.btnSignUp.setOnClickListener {
            controller.navigate(R.id.action_createAccountFragment_to_signUpFragment)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAuthResult(authResult: AuthResult){

    }

    companion object{
        const val SIGN_UP_TAG = "Tag_sign_up_231"
    }

}