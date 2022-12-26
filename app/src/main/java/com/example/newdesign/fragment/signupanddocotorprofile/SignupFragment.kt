package com.example.newdesign.fragment.signupanddocotorprofile

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.SignupfragmentBinding
import com.example.newdesign.model.register.CreateUser
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.RegisterViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: SignupfragmentBinding
    private val viewmodel: RegisterViewmodel by viewModels()
    var isValid = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = SignupfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        callBack()
    }


    private fun initButton() {

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.layoutHelp.setOnClickListener {
            findNavController().navigate(R.id.dialogBottomSheetFragment)
        }

        binding.btnSignUp.setOnClickListener {
            var fullNameEn = binding.etFullNameEn.text.toString()
            var fullNameAr = binding.etFullNameAR.text.toString()
            var mobileNumber = binding.etPhoneNumber.text.toString()
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            var confirmPassword = binding.confirmEtPassword.text.toString()
            if (isvalidateFeilds(
                    fullNameEn,
                    fullNameAr,
                    mobileNumber,
                    email,
                    password,
                    confirmPassword
                )
            ) {
                Toast.makeText(requireContext(), " logged", Toast.LENGTH_SHORT).show()
                val user=CreateUser(email,fullNameEn,password,mobileNumber,2)
                viewmodel.createUser("En",user)
                findNavController().navigate(R.id.otpFragment)
            } else {
                Toast.makeText(requireContext(), " not logged", Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun callBack() {
        viewmodel.response.observe(viewLifecycleOwner, Observer {

               when(it){

                   is Resource.Loading->{
                       showprogtessbar()
                   }

                   is Resource.sucess ->{

                   }
                   is Resource.Error ->{

                   }

               }

        })


    }

    private fun showprogtessbar() {
        TODO("Not yet implemented")
    }


    private fun isvalidateFeilds(
        fullNameEn: String,
        fullNameAr: String,
        mobileNumber: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {


        if (fullNameEn.trim().isNullOrEmpty()) {
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            // binding.textinputMobile.boxBackgroundMode= TextInputLayout.BOX_BACKGROUND_OUTLINE
            binding.etFullNameEn.hint = getString(R.string.required)
            binding.etFullNameEn.setHintTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false
        } else if (fullNameEn.trim().split(" ").size < 3) {
            binding.etFullNameEn.setText(null)
            binding.etFullNameEn.hint = getString(R.string.full_name_in_english_first_middle_last)
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.etFullNameEn.setHintTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false

        } else {
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext)
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }


        if (fullNameAr.trim().isNullOrEmpty()) {
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            // binding.textinputMobile.boxBackgroundMode= TextInputLayout.BOX_BACKGROUND_OUTLINE
            binding.etFullNameAR.hint = getString(R.string.required)
            binding.etFullNameAR.setHintTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext_error)

            isValid = false
        } else if (fullNameAr.trim().split(" ").size < 3) {
            binding.etFullNameAR.setText(null)
            binding.etFullNameAR.hint = getString(R.string.full_name_in_english_first_middle_last)
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.etFullNameAR.setHintTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false

        } else {
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext)
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }

        if (mobileNumber.trim().isEmpty()) {
            // binding.textinputPassword.error = "no *"
            binding.textinputPhoneNumber.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            //  binding.textinputPassword.error = null
            binding.textinputPhoneNumber.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }



        if (email.trim().isEmpty()) {
           // binding.textinputPassword.error = "no *"
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        }else if(!email.isValidEmail()){
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        }

        else {
          //  binding.textinputPassword.error = null
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }


        if (password.trim().isEmpty()) {
            binding.textinputPassword.error = "no *"
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else if (password.length < 6) {
            binding.textinputPassword.error = "passwored should > 6 "
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.textinputPassword.error = null
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        if (confirmPassword.trim().isEmpty()) {
            // binding.textinputPassword.error = "no *"
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else if (password.length < 6) {
            //binding.textinputPassword.error = "passwored should > 6 "
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else if (password != confirmPassword) {
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else {
            binding.textinputPassword.error = null
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        return isValid

    }


    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()


}