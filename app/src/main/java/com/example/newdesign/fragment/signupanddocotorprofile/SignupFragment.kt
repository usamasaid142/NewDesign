package com.example.newdesign.fragment.signupanddocotorprofile

import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.SignupfragmentBinding
import com.example.newdesign.model.register.CreateUser
import com.example.newdesign.model.register.CreateUserResponse
import com.example.newdesign.utils.Constans.NameAR
import com.example.newdesign.utils.Resource
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.RegisterViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: SignupfragmentBinding
    private val viewmodel: RegisterViewmodel by viewModels()
    private var user: CreateUser? = null
    @Inject
    lateinit var sp:SpUtil
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

        binding.ivShowpassword.setOnClickListener {

            if (binding.etPassword.transformationMethod is PasswordTransformationMethod) {
                binding.etPassword.transformationMethod = null
                binding.ivShowpassword.setImageResource(R.drawable.ic_eyehide)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod()
                binding.ivShowpassword.setImageResource(R.drawable.ic_eyeshow)

            }
        }
        binding.ivConfirmshowpassword.setOnClickListener {

            if (binding.confirmEtPassword.transformationMethod is PasswordTransformationMethod) {
                binding.confirmEtPassword.transformationMethod = null
                binding.ivConfirmshowpassword.setImageResource(R.drawable.ic_eyehide)
            } else {
                binding.confirmEtPassword.transformationMethod = PasswordTransformationMethod()
                binding.ivConfirmshowpassword.setImageResource(R.drawable.ic_eyeshow)

            }
        }


        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.layoutHelp.setOnClickListener {
            findNavController().navigate(R.id.dialogBottomSheetFragment)
        }


        binding.btnSignUp.setOnClickListener {
            if (binding.chTerms.isChecked) {
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
                    user = CreateUser(email, fullNameEn, password, mobileNumber, 2)
                    sp.saveUserNameInArabic(NameAR, fullNameAr)
                    viewmodel.registerUser("En", user!!)
                }


            } else {
                Toast.makeText(
                    requireContext(),
                    "check terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }

    private fun callBack() {
        viewmodel.response.observe(viewLifecycleOwner, Observer {


            when (it) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    it?.data?.let { response ->
                        val action =
                            SignupFragmentDirections.actionSignupFragmentToOtpFragment(
                                response.data,
                                user
                            )
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
            binding.tvFullNameError.text = getString(R.string.required)
            binding.tvFullNameError.visibility = View.VISIBLE
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false
        } else if (fullNameEn.trim().split(" ").size < 3) {
            binding.tvFullNameError.visibility = View.VISIBLE
            binding.tvFullNameError.text = getString(R.string.Enter_fullName_english)
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false

        } else {
            binding.tvFullNameError.visibility = View.GONE
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
            binding.tvFullNameARError.text = getString(R.string.required)
            binding.tvFullNameARError.visibility = View.VISIBLE
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext_error)

            isValid = false
        } else if (fullNameAr.trim().split(" ").size < 3) {
            binding.tvFullNameARError.text = getString(R.string.Enter_fullName_Arabic)
            binding.tvFullNameARError.visibility = View.VISIBLE
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false

        } else {
            binding.tvFullNameARError.visibility = View.GONE
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext)
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }

        if (mobileNumber.trim().isEmpty()) {
            binding.tvPohneError.text = getString(R.string.required)
            binding.tvPohneError.visibility = View.VISIBLE
            binding.textinputPhoneNumber.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else {
            binding.tvPohneError.visibility = View.GONE
            binding.textinputPhoneNumber.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }



        if (email.trim().isEmpty()) {
            binding.tvEmailError.text = getString(R.string.required)
            binding.tvEmailError.visibility = View.VISIBLE
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else if (!email.isValidEmail()) {
            binding.tvEmailError.text = getString(R.string.match_email)
            binding.tvEmailError.visibility = View.VISIBLE
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvEmailError.visibility = View.GONE
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }


        if (password.trim().isEmpty()) {
            binding.tvPasswordError.text = getString(R.string.required)
            binding.tvPasswordError.visibility = View.VISIBLE
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else if (password.length < 6) {
            binding.tvPasswordError.text = getString(R.string.lesstthan6number)
            binding.tvPasswordError.visibility = View.VISIBLE
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvPasswordError.visibility = View.GONE
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        if (confirmPassword.trim().isNullOrEmpty()) {
            binding.tvConfirmpasswordError.text = getString(R.string.required)
            binding.tvConfirmpasswordError.visibility = View.VISIBLE
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else if (confirmPassword.length < 6) {
            binding.tvConfirmpasswordError.text = getString(R.string.lesstthan6number)
            binding.tvConfirmpasswordError.visibility = View.VISIBLE
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else if (password != confirmPassword) {
            binding.tvConfirmpasswordError.text = getString(R.string.doesnotmatch)
            binding.tvPasswordError.text = getString(R.string.doesnotmatch)
            binding.tvConfirmpasswordError.visibility = View.VISIBLE
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else {
            binding.tvConfirmpasswordError.visibility = View.GONE
            binding.tvPasswordError.visibility = View.GONE
            binding.layoutConfirmpassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.confirmEtPassword.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                null,
                null
            )
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }

        return isValid

    }

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()


}