package com.example.newdesign.fragment.healthy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.newdesign.R
import com.example.newdesign.databinding.HealthEntityfragmentBinding
import com.example.newdesign.databinding.HealthyTopicsfragmentBinding
import com.example.newdesign.utils.Constans
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class HealthyTopicsFragment : Fragment() {


    private lateinit var binding:HealthyTopicsfragmentBinding
    private val args:HealthyTopicsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= HealthyTopicsfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDataToViews()
        initButton()
    }

    private fun initButton(){
        binding.ivArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun bindDataToViews() {
        binding.tvHealthTopics.text=args.healthdata?.title
        binding.tvHealthTopicsDetails.text=args.healthdata?.details
        Glide.with(binding.ivHealthTopics)
            .load("https://salamtechapi.azurewebsites.net/${args.healthdata?.imageURL}")
            .into(binding.ivHealthTopics)
    }


}