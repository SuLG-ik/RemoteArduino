package ru.sulgik.remotearduino.auth

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import org.apache.commons.validator.routines.EmailValidator
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.RemoteArduinoActivity
import ru.sulgik.remotearduino.database.FireDB
import ru.sulgik.remotearduino.databinding.RegistrationDialogModalBinding
import ru.sulgik.remotearduino.log.SystemLog

class RegistrationDialog : BottomSheetDialogFragment(), RemoteArduinoActivity.FragmentCallback {

    private fun dismiss(state : Int){
        activityCallback.onFinish(state)
        dismiss()
    }

    override fun toFinishFragment() {
        dismiss(if (user != null) Activity.RESULT_OK else Activity.RESULT_OK)
    }

    private lateinit var activityCallback: RemoteArduinoActivity.ActivityCallback

    private lateinit var binding : RegistrationDialogModalBinding

    private lateinit var db : FireDB
    private val user get() = db.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.registration_dialog_modal, container, false)

        val authVM by activityViewModels<AuthVM>()

        binding.authVM = authVM

        db = FireDB()

        binding.confirmButton.setOnClickListener {
            val email = authVM.email.value ?: ""
            val password = authVM.password1.value ?: ""
            val password2 = authVM.password2.value ?: ""

            log.debug(email)

            if (!emailIsValid(email) || password2 != password) {
                if (!emailIsValid(email)) {
                    binding.emailLayout.error = getString(R.string.invalid_email)
                }

                if (password2 != password) {
                    binding.password2Layout.error = getString(R.string.different_passwords)
                    binding.password2Layout.boxStrokeColor = Color.RED
                }
            }else{
                db.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    log.debug("createUserWithEmailAndPassword", email)
                    if(it.isSuccessful){
                        log.info("User has created", db.auth.currentUser?.email, it.result?.user?.email, email)
                        onCreateUser()
                    }else{
                        when(it.exception){
                            is FirebaseNetworkException -> {
                                setError(getString(R.string.internet_exception))
                            }
                            is FirebaseAuthUserCollisionException -> {
                                log.warning("FirebaseAuthUserCollisionException")
                                userExists()
                            }
                            is FirebaseAuthWeakPasswordException -> {
                                log.warning("FirebaseAuthUserCollisionException")
                                weekPassword()
                            }
                        }
                    }
                }
            }
        }

        binding.password.doOnTextChanged { text, _, _, _ ->
            binding.password2Layout.error = null
            binding.passwordLayout.error = null

            if (text.toString().length > 8)

            if(binding.password.text.toString() != binding.password2.text.toString()){
                binding.password2Layout.boxStrokeColor = context?.getColor(R.color.colorPrimaryDark)  ?: Color.BLUE
            }else{
                binding.password2Layout.boxStrokeColor = Color.GREEN
                binding.passwordLayout.boxStrokeColor = Color.GREEN
            }
        }

        binding.password2.doOnTextChanged { text, _, _, _ ->
            if(binding.password.text.toString() != binding.password2.text.toString()){
                binding.password2Layout.boxStrokeColor = context?.getColor(R.color.colorPrimaryDark)  ?: Color.BLUE
            }else{
                binding.password2Layout.boxStrokeColor = Color.GREEN
                binding.passwordLayout.boxStrokeColor = Color.GREEN
            }
        }

        binding.email.doOnTextChanged { text, _, _, _ ->
            removeError()
            binding.emailLayout.error = null
        }

        binding.email.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if(!hasFocus) {
                view as TextInputEditText
                val text = view.text.toString()
                if (text.isNotEmpty()) {
                    if (!emailIsValid(text)) {
                        binding.emailLayout.error = getString(R.string.invalid_email)
                    }
                }
            }
        }



        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityCallback = context as RemoteArduinoActivity.ActivityCallback
    }

    private fun weekPassword() {
        binding.password2Layout.error = "Week password"
        binding.passwordLayout.error = "Week password"
    }

    private fun onCreateUser(){
        dismiss(Activity.RESULT_OK)
    }

    private fun userExists(){
        setError(getString(R.string.user_has_existed), getString(R.string.try_log_in))
    }

    private fun removeError(){
        setError(null)
    }

    private fun setError(error: String?, solution : String? = null){
        binding.error = error
        binding.errorSolution = solution
    }

    private fun emailIsValid(email: String) = EmailValidator.getInstance().isValid(email)

    companion object{
        val log = SystemLog("RegistrationDialog")
    }

}