package com.example.newdesign.fragment.navstrepercontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.MedicalStatefragmentBinding
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalStateFragment : Fragment() {

    private lateinit var binding: MedicalStatefragmentBinding
    private val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= MedicalStatefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun  initButton(){
        binding.btnNext.setOnClickListener {
            Snackbar.make(requireView(), getString(R.string.datasentsuccessfully), Snackbar.LENGTH_SHORT).show()
            sharedDataViewmodel.getProfileStatus(3)
            binding.btnNext.visibility=View.GONE
            binding.imageView.visibility=View.GONE
        }
    }

    private fun callBack(){
        viewmodel.patientMedicalInfoResponse.observe(viewLifecycleOwner, Observer {response->
            when (response) {
                is Resource.sucess -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let {
//                        it.data?.profileStatus?.let { it1 ->
//                            sharedDataViewmodel.getProfileStatus(
//                                it1
//                            )
//                        }
//                        it.data?.profileStatus?.let { it1 ->
//                            sp.saveProfileStatus(
//                                Constans.PROFILE_STATUS,
//                                it1
//                            )
//                        }
                        Snackbar.make(
                            requireView(),
                            getString(R.string.datasentsuccessfully),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.locationFragment2)
                    }


                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    response.message?.let {
                        Snackbar.make(
                            requireView(),
                            "${response.message}",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

}