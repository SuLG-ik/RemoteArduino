package ru.sulgik.remotearduino.ui.first_start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase
import org.apache.commons.validator.routines.EmailValidator
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.android.scope.bindScope
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentSignInBinding
import ru.sulgik.remotearduino.databinding.FragmentSignUpBinding
import ru.sulgik.remotearduino.modules.authentication.AuthService
import ru.sulgik.remotearduino.modules.delegate.viewBindings
import ru.sulgik.remotearduino.ui.RemoteArduinoDialog

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : RemoteArduinoDialog(R.layout.fragment_sign_up) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_up, container, false)

    val auth : AuthService by inject()

    val binding by viewBindings(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.inputSignUpEmail.doOnTextChanged { _, _, _, _ ->
            emailError(null)
        }

        binding.inputSignUpPassword1.doOnTextChanged { _, _, _, _ ->
            password1Error(null)
        }

        binding.inputSignUpPassword2.doOnTextChanged { _, _, _, _ ->
            password2Error(null)
        }

        binding.btnSignUpNext.setOnClickListener {v ->
            emailLoadingShow()
            v.isClickable = false

            val email = binding.inputSignUpEmail.text.toString()

            if (!EmailValidator.getInstance().isValid(email)){
                emailError(getString(R.string.error_invalid_email))
                v.isClickable = true
                emailLoadingHide()
            }else{
                auth.signUp(email, "a").addOnFailureListener {
                    if (it is FirebaseAuthUserCollisionException){
                        emailError(getString(R.string.error_email_is_in_use))
                    }else{
                        toPasswordsBlock()
                    }
                    v.isClickable = true
                    emailLoadingHide()
                }
            }
        }

        binding.btnSignUpBack.setOnClickListener {v ->
            toEmailBlock()
        }

        binding.btnSignUpConfirm.setOnClickListener {v ->
            v.isClickable = false
            passwordLoadingShow()

            val email = binding.inputSignUpEmail.text.toString()
            val password1 = binding.inputSignUpPassword1.text.toString()
            val password2 = binding.inputSignUpPassword2.text.toString()

            if (!EmailValidator.getInstance().isValid(email)){
                emailError(getString(R.string.invalid_email))
                toEmailBlock()
            }

            if (password1.isEmpty()){
                password1Error(getString(R.string.error_get_a_password))
            }

            if (password2.isEmpty()){
                password2Error(getString(R.string.error_get_a_password_here_too))
            }

            if (password1.isNotEmpty()){
                if (password1 != password2)
                    password2Error(getString(R.string.different_passwords))
                else
                    auth.signUp(email, password1).addOnSuccessListener {
                        EventBus.getDefault().post(it)
                        dismiss()
                        passwordLoadingHide()
                        v.isClickable = true
                    }.addOnFailureListener {
                        when(it){
                            is FirebaseAuthUserCollisionException -> {
                                emailError(getString(R.string.error_email_is_in_use))
                                toEmailBlock()
                            }
                            is FirebaseAuthWeakPasswordException -> {
                                password1Error(getString(R.string.weak_password))
                            }
                            else -> {
                                showError(getString(R.string.error_internet))
                            }
                        }
                        v.isClickable = true
                        passwordLoadingShow()
                    }
            }

        }

    }

    private fun passwordLoadingShow(){
        binding.progressSignUpPassword.show()
    }

    private fun passwordLoadingHide(){
        binding.progressSignUpPassword.hide()
    }

    private fun emailLoadingShow(){
        binding.progressSignUpEmail.show()
    }

    private fun emailLoadingHide(){
        binding.progressSignUpEmail.hide()
    }

    private fun toPasswordsBlock(){
        binding.blocksFlipper.displayedChild = 1
    }


    private fun toEmailBlock(){
        binding.blocksFlipper.displayedChild = 0
    }

    private fun emailError(text : String?){
        binding.inputLayoutSignUpEmail.error = text
    }

    private fun password1Error(text : String?){
        binding.inputLayoutSignUpPassword1.error = text
    }

    private fun password2Error(text : String?){
        binding.inputLayoutSignUpPassword2.error = text
    }

    private fun showError(text : String){
        binding.errorSignUp.visibility = View.VISIBLE
        binding.errorSignUp.text = text
    }

    private fun hideError(){
        binding.errorSignUp.visibility = View.INVISIBLE
    }

}