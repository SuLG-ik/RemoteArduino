package ru.sulgik.remotearduino.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import ru.sulgik.remotearduino.R
import ru.sulgik.remotearduino.database.FireVM
import ru.sulgik.remotearduino.databinding.FragmentAccountBinding

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    lateinit var binding : FragmentAccountBinding
    val db by activityViewModels<FireVM>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.user = db.user



        return binding.root
    }

    private fun updateUI(){

    }

}
