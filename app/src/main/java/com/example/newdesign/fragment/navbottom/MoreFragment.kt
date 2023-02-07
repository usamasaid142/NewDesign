package com.example.newdesign.fragment.navbottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.MorefragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {

private lateinit var binding:MorefragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= MorefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }


    private fun initButton(){

        binding.layoutLanguage.setOnClickListener {
            val action=MoreFragmentDirections.actionMoreFragmentToDialogBottomSheetFragment("lang")
            findNavController().navigate(action)
        }
        binding.layoutSignOut.setOnClickListener {
            val action=MoreFragmentDirections.actionMoreFragmentToDialogBottomSheetFragment("signout")
            findNavController().navigate(action)
        }

        binding.layoutChangePassword.setOnClickListener {
            findNavController().navigate(R.id.changepasswordFragment)
        }
        binding.layoutTermsConditions.setOnClickListener {
            findNavController().navigate(R.id.webViewFragment)
        }
        binding.layoutNextAvailableBooking.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment3)
        }

    }

}