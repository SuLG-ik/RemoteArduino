package ru.sulgik.remotearduino.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment.STYLE_NO_FRAME
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.tapadoo.alerter.Alerter
import com.tapadoo.alerter.OnHideAlertListener
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.RemoteArduinoActivity
import ru.sulgik.remotearduino.databinding.ActivityRegistrationBinding
import ru.sulgik.remotearduino.log.SystemLog

class RegistrationActivity : RemoteArduinoActivity(R.layout.activity_registration), RemoteArduinoActivity.ActivityCallback {

    override val TAG: String = REGISTRATION_ACTIVITY_TAG

    lateinit var google : GoogleSignInClient

    val binding by viewDataBindings<ActivityRegistrationBinding>(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authVM : AuthVM by viewModels()

        val vm  by viewModels<AuthVM>()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply{
            requestIdToken(getString(R.string.default_web_client_id))
            requestEmail()
        }.build()

        google = GoogleSignIn.getClient(this, gso)

        binding.googleLogIn.setOnClickListener {
            signInByGoogle()
        }

        binding.signUp.setOnClickListener {
            RegistrationDialog().apply {
                setStyle(STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
            }.show(supportFragmentManager,REG_TAG)
        }

        binding.logIn.setOnClickListener {

            LogInDialog().apply {
                setStyle(STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
            }.show(supportFragmentManager, LOG_IN_TAG)
        }


    }

    private fun signInByGoogle(){
        startActivityForResult(google.signInIntent,
            GOOGLE_SIGN_IN_RC
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){

            GOOGLE_SIGN_IN_RC -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseWithGoogle(account!!)
                }catch (e:ApiException){
                    log.warning("Google sign in error")
                    Alerter.create(this)
                        .setTitle(R.string.google)
                        .setText(R.string.google_sign_in_error)
                        .setIcon(R.drawable.ic_google)
                        .setDuration(2500)
                        .show()
                }

            }

        }
    }

    private fun firebaseWithGoogle(account: GoogleSignInAccount) {
        log.info("Firebase sign in with the Google id: ${account.id!!}")

        val alert = Alerter.create(this)
            .setTitle(getString(R.string.authentication))
            .setText(getString(R.string.wait))
            .enableProgress(true)
            .setProgressColorInt(R.color.text)
            .setDuration(2000)
            .show()

        db.auth.signInWithCredential(GoogleAuthProvider.getCredential(account.id, null)).addOnCompleteListener(this) {
            if (it.isSuccessful){
                log.info("Firebase signing in with the Google has finished")
            }else{
                log.info("Firebase signing in with the Google hasn't finished", it.exception?.message)
                Alerter.create(this)
                    .setTitle(R.string.google)
                    .setText(R.string.google_sign_in_error)
                    .setIcon(R.drawable.ic_google)
                    .setDuration(2500)
                    .show()
            }
        }

    }

    override fun onBackPressed() {

    }


    private fun onUserCreated(){
        finish()
    }


    override fun onCanceled() {

    }

    override fun onCompleted() {
        Alerter.create(this)
            .setText(userName ?: getString(R.string.unknown))
            .setTitle(getString(R.string.you_are_signing_in))
            .enableProgress(true)
            .setOnHideListener(OnHideAlertListener {onUserCreated()})
            .setDuration(500)
            .show()
    }

    private val userName get()= db.auth.currentUser?.displayName ?: db.auth.currentUser?.email

    companion object{
        const val GOOGLE_SIGN_IN_RC = 7897

        const val REGISTRATION_ACTIVITY_TAG = "RegistrationActivity:4845645"

        val log = SystemLog("RegistrationActivity")

        const val REG_TAG = "RegistrationActivity:REG:TAG:197789"

        const val LOG_IN_TAG = "RegistrationActivity:LOG:TAG:789561"
    }

}
