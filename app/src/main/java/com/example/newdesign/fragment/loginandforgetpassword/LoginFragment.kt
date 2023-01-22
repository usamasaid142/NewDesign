package com.example.newdesign.fragment.loginandforgetpassword

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.LoginfragmentBinding
import com.example.newdesign.model.register.LoginUser
import com.example.newdesign.utils.Constans.NameAR
import com.example.newdesign.utils.Constans.UserLOGIN
import com.example.newdesign.utils.Resource
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.RegisterViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: LoginfragmentBinding
     val viewmodel:RegisterViewmodel by viewModels()

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
                    }
                    findNavController().navigate(R.id.homeFragment)

                    Toast.makeText(requireContext(), " your are login", Toast.LENGTH_SHORT).show()
                }

                is Resource.Error->{
                    hideprogressbar()
                    loginresponse.message?.let {
                        Log.e("msg : ",it)

                    }
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