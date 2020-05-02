package ru.sulgik.remotearduino.ui.auth

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.core.view.get
import androidx.transition.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up_email.*
import kotlinx.android.synthetic.main.fragment_sign_up_passwords.*

import ru.sulgik.remotearduino.R

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_up, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sceneRoot = view.findViewById<ViewGroup>(R.id.sceneRoot)
        val sceneA = Scene.getSceneForLayout(sceneRoot, R.layout.fragment_sign_up_passwords, requireContext())
        val sceneB = Scene.getSceneForLayout(sceneRoot, R.layout.fragment_sign_up_passwords, requireContext())
        val slideB = Slide().apply {
            slideEdge = Gravity.END
            addTarget(R.id.blockPasswords)
            addTarget(R.id.btnConfirm)
        }
        val slideA = Slide().apply {
            addTarget(R.id.blockEmails)
            addTarget(R.id.btnSignUpNext)
        }
        val set = TransitionSet().apply {
            addTransition(slideA)
            addTransition(slideB)
            duration = 250
        }
        val s = view.findViewById<ViewGroup>(R.id.sceneToRoot)
        val animator = ValueAnimator.ofInt(s.measuredHeight, 100)
        animator.addUpdateListener {
            s.layoutParams.height = it.animatedValue as Int
        }
        animator.duration = 250
        btnSignUpNext.setOnClickListener {
            animator.start()
        }
    }

}
