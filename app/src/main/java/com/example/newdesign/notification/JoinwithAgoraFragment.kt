package com.example.newdesign.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.JoinwithAgorafragmentBinding
import com.example.newdesign.fragment.loginandforgetpassword.LoginFragmentDirections
import com.example.newdesign.notification.model.NotificationResponse
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.DateUtils
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.RegisterViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinwithAgoraFragment : Fragment() {

    private lateinit var binding:JoinwithAgorafragmentBinding
    val viewmodel: DialogBottomSheetViewmodel by viewModels()
    private var notificationResponse:NotificationResponse?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= JoinwithAgorafragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callBack()
        initButton()
    }

    private fun initButton() {
        binding.next.setOnClickListener {
            val action=JoinwithAgoraFragmentDirections.actionJoinwithAgoraFragmentToVideoCallingFragment(notificationResponse)
            findNavController().navigate(action)
        }
    }


    private fun callBack() {

        viewmodel.notificatioResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is Resource.Loading -> {
                }

                is Resource.sucess -> {
                   binding.etJoin.setText(response.data?.channelName)
                    notificationResponse=response.data
                }

                is Resource.Error -> {
                    Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })

        viewmodel.getAgraMeeting(3)

    }


}