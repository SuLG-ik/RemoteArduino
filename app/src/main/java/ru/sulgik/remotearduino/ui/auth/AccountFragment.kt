package ru.sulgik.remotearduino.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentAccountBinding
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.modules.delegate.viewBindings

class AccountFragment : Fragment(R.layout.fragment_account) {

    val binding by viewBindings(FragmentAccountBinding::bind)

    val auth by inject<AuthService>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = Navigation.findNavController(view)
        binding.btnCreateProfile.setOnClickListener {
            controller.navigate(R.id.action_accountFragment_to_createAccountFragment)
        }
    }

}

