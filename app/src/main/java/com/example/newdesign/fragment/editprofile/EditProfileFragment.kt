package com.example.newdesign.fragment.editprofile

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newdesign.R
import com.example.newdesign.databinding.EditProfilefragmentBinding
import com.example.newdesign.fragment.navstrepercontent.LegalDocFragment
import com.example.newdesign.fragment.navstrepercontent.PersonalInfoFragment
import com.example.newdesign.fragment.navstrepercontent.SpecialtyFragment

class EditProfileFragment : Fragment() {

    private lateinit var binding:EditProfilefragmentBinding
    val profilefragment=PersonalInfoFragment()
    val legalDocFragment=LegalDocFragment()
    val specialtyFragment=SpecialtyFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= EditProfilefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,profilefragment)
            commit()
        }
        initButton()
    }

    private fun initButton(){


        binding.layoutProfileInfo.setOnClickListener {
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_buttonsearch)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutLocation.setBackgroundResource( R.drawable.bg_help)
            binding.tvLocation.setTextColor(Color.parseColor("#262D70"))
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_help)
            binding.tvMedicalState.setTextColor(Color.parseColor("#262D70"))

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment,profilefragment)
                addToBackStack(null)
                commit()
            }

        }

        binding.layoutLocation.setOnClickListener {
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_buttonsearch)
            binding.tvLocation.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_help)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#262D70"))
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_help)
            binding.tvMedicalState.setTextColor(Color.parseColor("#262D70"))
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment,legalDocFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.layoutMedicalState.setOnClickListener {
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_buttonsearch)
            binding.tvMedicalState.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_help)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#262D70"))
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_help)
            binding.tvLocation.setTextColor(Color.parseColor("#262D70"))

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment,specialtyFragment)
                addToBackStack(null)
                commit()
            }
        }

    }

}