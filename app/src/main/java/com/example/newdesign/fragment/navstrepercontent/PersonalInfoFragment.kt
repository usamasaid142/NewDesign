package com.example.newdesign.fragment.navstrepercontent

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.PersonalinfofragmentBinding
import com.example.newdesign.model.register.CreateUser

class PersonalInfoFragment : Fragment() {

    private lateinit var binding:PersonalinfofragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= PersonalinfofragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initbutton()
    }


    private fun initbutton()
    {
        val fullNameEn = binding.etFullNameEn.text.toString()
        val fullNameAr = binding.etFullNameAR.text.toString()
        val mobileNumber = binding.etPhoneNumber.text.toString()
        val email = binding.etEmail.text.toString()
        val gender = binding.etGender.text.toString()
        val birthDate = binding.etDateOfbirth.text.toString()
        val nationality = binding.etNationality.text.toString()

        if(fullNameEn.trim().isEmpty() || fullNameAr.trim().isEmpty()|| mobileNumber.trim().isEmpty()
            ||  email.trim().isEmpty()|| gender.trim().isEmpty() ||birthDate.trim().isEmpty() ||nationality.trim().isEmpty()){
            binding.btnNext.isEnabled=false
            binding.btnNext.background= getDrawable(requireContext(),R.color.button_color_disabled)

        }else{
            binding.btnNext.isEnabled = true
            binding.btnNext.background= getDrawable(requireContext(),R.color.red)
        }
        binding.btnNext.setOnClickListener {
        //    findNavController().navigate(R.id.specialtyFragment)


            if (isvalidateFeilds(
                    fullNameEn,
                    fullNameAr,
                    mobileNumber,
                    email,
                    gender,
                    birthDate,
                    nationality
                )
            ) {
              //  user = CreateUser(email, fullNameEn, password, mobileNumber, 2)
              //  viewmodel.registerUser("En", user!!)
            }else{

            }


        }

    }



    private fun isvalidateFeilds(
        fullNameEn: String,
        fullNameAr: String,
        mobileNumber: String,
        email: String,
        gender: String,
        dateOfBirth:String,
        nationality:String
    ): Boolean {

            var isValid=true

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
        if (gender.trim().isEmpty()) {
            binding.tvGenderError.text = getString(R.string.required)
            binding.tvGenderError.visibility = View.VISIBLE
            binding.layoutGender.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etGender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvGenderError.visibility = View.GONE
            binding.layoutGender.setBackgroundResource(R.drawable.bg_edittext)
            binding.etGender.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }
        if (dateOfBirth.trim().isEmpty()) {
            binding.tvDateOfBirthError.text = getString(R.string.required)
            binding.tvDateOfBirthError.visibility = View.VISIBLE
            binding.layoutDateOfbirth.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etDateOfbirth.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvDateOfBirthError.visibility = View.GONE
            binding.layoutDateOfbirth.setBackgroundResource(R.drawable.bg_edittext)
            binding.etDateOfbirth.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        if (nationality.trim().isEmpty()) {
            binding.tvNationaltyError.text = getString(R.string.required)
            binding.tvNationaltyError.visibility = View.VISIBLE
            binding.layoutNationality.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etNationality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvNationaltyError.visibility = View.GONE
            binding.layoutNationality.setBackgroundResource(R.drawable.bg_edittext)
            binding.etNationality.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }



        return isValid

    }





    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()


}