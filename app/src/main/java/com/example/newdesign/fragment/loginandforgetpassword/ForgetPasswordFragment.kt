package com.example.newdesign.fragment.loginandforgetpassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.ForgetPasswordfragmentBinding
import com.example.newdesign.model.register.UserForgetInfo
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.RegisterViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: ForgetPasswordfragmentBinding
    private val viewmodel: RegisterViewmodel by viewModels()
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
        otpCallBack()
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
            if (binding.etMobile.text.toString().isNullOrEmpty()){
                return@setOnClickListener
            }
         //   viewmodel.Sentotp("en", binding.etMobile.text.toString())
        }

    }

    private fun otpCallBack(){
        viewmodel.otpResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                   showprogtessbar()
                }

                is Resource.sucess -> {
                       hideprogressbar()
                    it?.data?.let { response ->
                        val userForgetInfo=UserForgetInfo(response.messageCode,binding.etMobile.text.toString())
                      val action=ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToOtpFragment(null,null,userForgetInfo)
                        findNavController().navigate(action)
                    }
                }
                is Resource.Error -> {
                  hideprogressbar()
                    it.message?.let { error ->
                        Log.e("error", error)
                    }
                }

            }

        })

    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.INVISIBLE
    }


}