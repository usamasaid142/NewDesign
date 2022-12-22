package com.example.newdesign.fragment.loginandforgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.ForgetPasswordfragmentBinding


class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: ForgetPasswordfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= ForgetPasswordfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }

    private fun initButton(){

        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.layoutHelp.setOnClickListener {
            findNavController().navigate(R.id.dialogBottomSheetFragment)
        }

        binding.etMobile.doOnTextChanged { text, start, before, count ->

            binding.textinputForgetpassword.setBackgroundResource(R.drawable.bg_edittext_writting)
        }

        binding.btnSend.setOnClickListener {
            findNavController().navigate(R.id.otpFragment)
        }

    }

}