package com.example.newdesign.fragment.navbottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.R
import com.example.newdesign.databinding.MorefragmentBinding
import com.example.newdesign.utils.SpUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private lateinit var binding: MorefragmentBinding

    @Inject
    lateinit var sp: SpUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = MorefragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = sp.getUser()?.name
        binding.ivPatientProfile.load("https://salamtechapi.azurewebsites.net/${sp.getUser()?.image}") {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.ic_profile)
            transformations(CircleCropTransformation())
        }
            initButton()
        }


        private fun initButton() {

            binding.layoutLanguage.setOnClickListener {
                val action =
                    MoreFragmentDirections.actionMoreFragmentToDialogBottomSheetFragment("lang")
                findNavController().navigate(action)
            }

            binding.layoutCallUs.setOnClickListener {
                val action =
                    MoreFragmentDirections.actionMoreFragmentToDialogBottomSheetFragment("help")
                findNavController().navigate(action)
            }

            binding.layoutSignOut.setOnClickListener {
                val action =
                    MoreFragmentDirections.actionMoreFragmentToDialogBottomSheetFragment("signout")
                findNavController().navigate(action)
            }

            binding.layoutChangePassword.setOnClickListener {
                val action = MoreFragmentDirections.actionMoreFragmentToChangepasswordFragment(0)
                findNavController().navigate(action)
            }
            binding.layoutTermsConditions.setOnClickListener {
                findNavController().navigate(R.id.webViewFragment)
            }
//            binding.layoutNextAvailableBooking.setOnClickListener {
//                findNavController().navigate(R.id.editProfileFragment)
//            }

        }

    }