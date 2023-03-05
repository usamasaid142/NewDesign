package com.example.newdesign.fragment.editprofile

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.newdesign.R
import com.example.newdesign.databinding.EditProfilefragmentBinding
import com.example.newdesign.fragment.navstrepercontent.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding:EditProfilefragmentBinding
    private lateinit var navController: NavController
    val profilefragment=PersonalInfoFragment()
    val location=LocationFragment()
    val medicalStateFragment=MedicalStateFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= EditProfilefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigationBottom()
        initButton()
    }

    private fun setupNavigationBottom() {
//        val navHostFragment =
//            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
//        navController = navHostFragment.navController
//
    }


    private fun initButton(){


        binding.layoutProfileInfo.setOnClickListener {
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutLocation.setBackgroundResource( R.drawable.bg_help)
            binding.tvLocation.setTextColor(Color.parseColor("#262D70"))
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_help)
            binding.tvMedicalState.setTextColor(Color.parseColor("#262D70"))

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment,binding.fragment.getFragment<Fragment>().apply {
                    this.id==R.id.personalInfoFragment2
                })

                addToBackStack(null)
                commit()
            }

        }

        binding.layoutLocation.setOnClickListener {
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvLocation.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_help)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#262D70"))
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_help)
            binding.tvMedicalState.setTextColor(Color.parseColor("#262D70"))
            binding.fragment.getFragment<Fragment>().apply {
                this.id==R.id.personalInfoFragment2==null

            }
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment,location)
                addToBackStack(null)
                commit()
            }

        }

        binding.layoutMedicalState.setOnClickListener {
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvMedicalState.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_help)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#262D70"))
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_help)
            binding.tvLocation.setTextColor(Color.parseColor("#262D70"))
            binding.fragment.getFragment<Fragment>().apply {
                this.id==R.id.personalInfoFragment2==null
            }
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment,medicalStateFragment)
                addToBackStack(null)
                commit()
            }


        }

    }

}