package com.example.newdesign.fragment.editprofile

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.EditProfilefragmentBinding
import com.example.newdesign.fragment.navstrepercontent.*
import com.example.newdesign.viewmodel.SharedDataViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding: EditProfilefragmentBinding
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private lateinit var navController: NavController
    val location = LocationFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = EditProfilefragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }


    private fun initButton() {

        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.moreFragment)
        }


        binding.layoutProfileInfo.setOnClickListener {
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_help)
            binding.tvLocation.setTextColor(Color.parseColor("#262D70"))
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_help)
            binding.tvMedicalState.setTextColor(Color.parseColor("#262D70"))
            sharedDataViewmodel.getUpdateProfileStatus(true)
            setupNavigationBottom(1)
        }

        binding.layoutLocation.setOnClickListener {
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvLocation.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_help)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#262D70"))
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_help)
            binding.tvMedicalState.setTextColor(Color.parseColor("#262D70"))
            sharedDataViewmodel.getUpdateProfileStatus(true)
            setupNavigationBottom(2)

        }

        binding.layoutMedicalState.setOnClickListener {
            binding.layoutMedicalState.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvMedicalState.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutProfileInfo.setBackgroundResource(R.drawable.bg_help)
            binding.tvProfileInfo.setTextColor(Color.parseColor("#262D70"))
            binding.layoutLocation.setBackgroundResource(R.drawable.bg_help)
            binding.tvLocation.setTextColor(Color.parseColor("#262D70"))
            sharedDataViewmodel.getUpdateProfileStatus(true)
            setupNavigationBottom(3)

        }

    }


    private fun setupNavigationBottom(type: Int) {

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.editProfile_NavContainer) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_editprofile)

        when (type) {
            1 -> {
                navGraph.setStartDestination(R.id.personalInfoFragment2)
            }
            2 -> {
                navGraph.setStartDestination(R.id.locationFragment2)
            }
            3 -> {
                navGraph.setStartDestination(R.id.medicalStateFragment2)
            }
        }

        navHostFragment.navController.graph = navGraph

    }

}