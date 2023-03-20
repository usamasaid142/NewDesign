package com.example.newdesign.fragment.loginandforgetpassword

import android.os.Bundle
import android.service.autofill.UserData
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.LoginfragmentBinding
import com.example.newdesign.model.register.LoginUser
import com.example.newdesign.utils.Constans.TOKEN
import com.example.newdesign.utils.Constans.UserLOGIN
import com.example.newdesign.utils.DateUtils
import com.example.newdesign.utils.Resource
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.RegisterViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: LoginfragmentBinding
     val viewmodel:RegisterViewmodel by viewModels()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()

    @Inject
    lateinit var sp: SpUtil

    companion object{
        var instance:LoginFragment?=null

    }
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
        instance=this
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

        binding.next.setOnClickListener {
            val mobile = binding.textinputMobile.editText?.text.toString()
            val password = binding.textinputPassword.editText?.text.toString()

            if (isvalidateFeilds(mobile, password)) {
                val userlogin=LoginUser("",password,mobile,3)
                viewmodel.loginUser("en",userlogin)
            }

        }

        binding.layoutHelp.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToDialogBottomSheetFragment("help")
            findNavController().navigate(action)
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

            binding.etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            binding.textinputMobile.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.tvFullNameError.text = getString(R.string.required)
            binding.tvFullNameError.visibility = View.VISIBLE

            isValid=false

        } else {
            binding.textinputMobile.error = null
            binding.tvFullNameError.visibility = View.GONE
            binding.textinputMobile.setBackgroundResource(R.drawable.bg_edittext)
            binding.etMobile.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true

        }

        if (password.trim().isEmpty()) {
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            binding.tvPasswordError.text = getString(R.string.required)
            binding.tvPasswordError.visibility = View.VISIBLE

            isValid = false
        } else if (password.length < 6) {
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            binding.tvPasswordError.text = getString(R.string.lesstthan6number)
            binding.tvPasswordError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.textinputPassword.error = null
            binding.tvPasswordError.visibility = View.GONE
            binding.layoutPassword.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        return isValid

    }

    private fun callBack(){

        viewmodel.loginResponse.observe(viewLifecycleOwner, Observer {loginresponse->
            when(loginresponse){

                is Resource.Loading->{
                    showprogtessbar()
                }

                is Resource.sucess->{
                    hideprogressbar()
                    loginresponse.let {
                        it.data?.data?.let { it1 -> sp.save(UserLOGIN, it1) }
                        loginresponse.data?.data?.let { it1 -> sp.saveUserToken(TOKEN, it1.token) }
                    }
                    loginresponse.data!!?.data?.let { DateUtils.setToken(it.token) }
                    sharedDataViewmodel.getProfileStatus(loginresponse.data?.data?.profileStatus!!)
                 if (loginresponse.data?.data?.profileStatus!!<2) {
                        findNavController().navigate(R.id.docotorProfileFragment)
                    }else{
                        findNavController().navigate(R.id.homeFragment)
                    }

                    Toast.makeText(requireContext(), " your are login Successfully", Toast.LENGTH_SHORT).show()
                }

                is Resource.Error->{
                    hideprogressbar()
                    Snackbar.make(requireView(), "${loginresponse.message}", Snackbar.LENGTH_SHORT).show()
                }
            }

        })

    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
    }


}