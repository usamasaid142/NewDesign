package com.example.newdesign.fragment.signupanddocotorprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.DocotorProfilefragmentBinding


class DocotorProfileFragment : Fragment() {

private lateinit var binding:DocotorProfilefragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DocotorProfilefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton()
    {

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.specialtyFragment)
        }

    }

}