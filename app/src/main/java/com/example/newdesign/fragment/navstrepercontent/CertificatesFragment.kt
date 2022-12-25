package com.example.newdesign.fragment.navstrepercontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.CertificatesfragmentBinding


class CertificatesFragment : Fragment() {

    private lateinit var binding:CertificatesfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= CertificatesfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initbutton()
    }
    private fun initbutton()
    {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.legalDocFragment)
        }

    }

}