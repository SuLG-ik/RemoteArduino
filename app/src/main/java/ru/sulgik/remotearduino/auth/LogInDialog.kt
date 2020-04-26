package ru.sulgik.remotearduino.auth

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import org.apache.commons.validator.routines.EmailValidator
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.RemoteArduinoActivity
import ru.sulgik.remotearduino.database.FireDB
import ru.sulgik.remotearduino.databinding.LogInDialogModalBinding
import ru.sulgik.remotearduino.log.SystemLog
import kotlin.math.min

class LogInDialog : BottomSheetDialogFragment(){

    private lateinit var binding: LogInDialogModalBinding
    private lateinit var db : FireDB
    private val user get()= db.auth.currentUser
    private val name get()= user?.displayName ?: user?.email
    private lateinit var callback : RemoteArduinoActivity.ActivityCallback


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.log_in_dialog_modal, container, false)
        val authVM by activityViewModels<AuthVM>()

        db = FireDB()

        binding.authVM = authVM

        binding.confirmButton.setOnClickListener {
            val email = authVM.email.value ?: ""
            val password = authVM.password1.value ?: ""

            db.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    onUserCreated()
                } else {
                    when (it.exception) {
                        is FirebaseNetworkException -> {
                            setError(getString(R.string.internet_exception))
                            log.debug((it.exception as FirebaseNetworkException).message)
                        }
                        is FirebaseAuthInvalidUserException -> {
                            setError(getString(R.string.check_email_password))
                            log.debug((it.exception as FirebaseAuthInvalidUserException).message)
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            setError(getString(R.string.check_email_password))
                            log.debug((it.exception as FirebaseAuthInvalidCredentialsException).message)
                        }
                        else ->{
                            log.debug(it.exception?.message)
                        }
                    }
                }

                log.debug(email, password)
            }

        }

        binding.email.doOnTextChanged { text, start, count, after ->
            setEmailError(null)
        }

        binding.email.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if(!hasFocus) {
                view as TextInputEditText
                val text = view.text.toString()
                if (text.isNotEmpty()) {
                    if (emailIsValid(text)) {
                        setEmailError( getString(R.string.invalid_email))
                    }
                }
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as RemoteArduinoActivity.ActivityCallback
    }

    private fun setPasswordError(error: String?){
        binding.passwordLayout.error = error
    }

    private fun setEmailError(error : String?){
        binding.emailLayout.error = error
    }

    private fun setError(error : String?, solution : String? = null){
        binding.error = error
        binding.errorSolution = solution
    }

    private fun emailIsValid(email : String) = EmailValidator.getInstance().isValid(email)

    private fun onUserCreated(){
        dismiss(RESULT_OK)
    }

    private fun dismiss(state : Int){
        callback.onFinish(state)
        dismiss()
    }

    companion object{
        val log = SystemLog("LogInDialog")
    }
}