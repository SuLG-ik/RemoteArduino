package ru.sulgik.remotearduino.ui.first_start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.delay
import org.apache.commons.validator.routines.EmailValidator
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.core.context.stopKoin

import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentSignInBinding
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.modules.delegate.viewBindings
import ru.sulgik.remotearduino.ui.RemoteArduinoDialog
import javax.xml.validation.Validator

/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : RemoteArduinoDialog(R.layout.fragment_sign_in) {

    private val auth by inject<AuthService>()
    private val binding by viewBindings(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.inputSignInEmail.doOnTextChanged { _, _, _, _ ->
            hideError()
        }

        binding.inputSignInPassword.doOnTextChanged { _, _, _, _ ->
            hideError()
        }


        binding.btnSignInConfirm.setOnClickListener { b->
            b.isClickable = false
            showLoading()
            val email = binding.inputSignInEmail.text.toString()
            val password = binding.inputSignInPassword.text.toString()

            auth.signIn(email, password).addOnSuccessListener {
                EventBus.getDefault().post(it)
                hideLoading()
                b.isClickable = true
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidUserException -> {
                        showError(getString(R.string.error_invaid_user))
                    }
                    else -> {
                        showError(getString(R.string.error_internet))
                    }
                }
                b.isClickable = true
                hideLoading()
            }
        }
    }

    private fun showLoading(){
        binding.contentLoadingProgressBar.show()
    }

    private fun hideLoading(){
        binding.contentLoadingProgressBar.hide()
    }

    private fun showError(text : String){
        binding.errorSignIn.visibility = View.VISIBLE
        binding.errorSignIn.text = text
    }

    private fun hideError(){
        binding.errorSignIn.visibility = View.INVISIBLE
    }
}
