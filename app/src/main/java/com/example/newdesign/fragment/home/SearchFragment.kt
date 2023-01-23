package com.example.newdesign.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.SearchDoctorsAdapter
import com.example.newdesign.adapter.SearchServicesAdapter
import com.example.newdesign.databinding.SearchfragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchfragmentBinding
    private lateinit var searchServicesAdapter: SearchServicesAdapter
    private lateinit var searchDoctorsAdapter: SearchDoctorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = SearchfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        servicesRecylerview()
        searchByServices()
        doctorsRecylerview()
        doctorSearches()
    }

    private fun initButton() {
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.layoutFilter.setOnClickListener {
            findNavController().navigate(R.id.filterFragment)
        }

    }

    private fun servicesRecylerview() {
        searchServicesAdapter = SearchServicesAdapter()
        binding.rvSearchServices.apply {
            adapter = searchServicesAdapter
            setHasFixedSize(true)
        }
    }

    private fun doctorsRecylerview() {
        searchDoctorsAdapter = SearchDoctorsAdapter()
        binding.rvDoctors.apply {
            adapter = searchDoctorsAdapter
            layoutManager=LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun searchByServices() {

        val list = mutableListOf<String>()

        list.add(
            getString(R.string.Clinic_Booking),
        )
        list.add(
            getString(R.string.Home_Visit)
        )
        list.add(
            getString(R.string.Chat),

            )
        list.add(
            getString(R.string.Call),
        )
        list.add(
            getString(R.string.Video_Call)
        )
        searchServicesAdapter.submitList(list)
        searchServicesAdapter.notifyDataSetChanged()

    }

    private fun doctorSearches() {

        val list = mutableListOf<String>()

        list.add(
            getString(R.string.osama),
        )
        list.add(
            getString(R.string.said)
        )

        searchDoctorsAdapter.submitList(list)
        searchDoctorsAdapter.notifyDataSetChanged()

    }


}