package com.example.newdesign.fragment.signupanddocotorprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.SignupfragmentBinding


class SignupFragment : Fragment() {

private lateinit var binding:SignupfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= SignupfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }


    private fun initButton(){

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.layoutHelp.setOnClickListener {
            findNavController().navigate(R.id.dialogBottomSheetFragment)
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.otpFragment)
        }

    }
}