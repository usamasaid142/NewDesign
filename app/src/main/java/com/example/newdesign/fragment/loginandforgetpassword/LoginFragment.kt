package com.example.newdesign.fragment.loginandforgetpassword

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.LoginfragmentBinding



class LoginFragment : Fragment() {


    private lateinit var binding: LoginfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = LoginfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()

    }

    private fun initButton() {


        binding.ivShowpassword.setOnClickListener {

            if (binding.etPassword.transformationMethod is PasswordTransformationMethod) {
                binding.etPassword.transformationMethod = null
                binding.ivShowpassword.setImageResource(R.drawable.ic_eyehide)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod()
                binding.ivShowpassword.setImageResource(R.drawable.ic_eyeshow)

            }
        }

        binding.next.setOnClickListener {
            //   findNavController().navigate(R.id.loginFragment)
            val mobile = binding.textinputMobile.editText?.text.toString()
            val password = binding.textinputPassword.editText?.text.toString()

            if (isvalidateFeilds(mobile, password)) {
                Toast.makeText(requireContext(), " logged", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), " not logged", Toast.LENGTH_SHORT).show()
            }

        }

        binding.layoutHelp.setOnClickListener {
            findNavController().navigate(R.id.dialogBottomSheetFragment)
        }
        binding.tvForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)
        }
        binding.tvSignup.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }

    }

    private fun isvalidateFeilds(mobile: String, password: String): Boolean {

        var isValid=true

        if (mobile.trim().isNullOrEmpty()) {

//            binding.textinputMobile.error = "no *"
            binding.etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            // binding.textinputMobile.boxBackgroundMode= TextInputLayout.BOX_BACKGROUND_OUTLINE
            binding.textinputMobile.setBackgroundResource(R.drawable.bg_edittext_error)

            isValid=false

        } else {
            binding.textinputMobile.error = null
            binding.textinputMobile.setBackgroundResource(R.drawable.bg_edittext)
            binding.etMobile.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true

        }

        if (password.trim().isEmpty()) {
//            binding.textinputPassword.error = "no *"
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else if (password.length < 6) {
//            binding.textinputPassword.error = "passwored should > 6 "
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.textinputPassword.error = null
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        return isValid

    }
}