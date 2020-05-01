package ru.sulgik.remotearduino.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.databinding.FragmentAccountBinding

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    lateinit var binding : FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)



        return binding.root
    }

    private fun updateUI(){

    }

}
