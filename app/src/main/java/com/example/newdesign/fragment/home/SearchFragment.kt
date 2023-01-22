package com.example.newdesign.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.SearchServicesAdapter
import com.example.newdesign.adapter.ServicesAdapter
import com.example.newdesign.databinding.ItemLayoutSliderBinding
import com.example.newdesign.databinding.SearchfragmentBinding
import com.example.newdesign.model.ImageServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchfragmentBinding
    private lateinit var searchServicesAdapter: SearchServicesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= SearchfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        servicesRecylerview()
        searchByServices()
    }

    private fun initButton()
    {
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun servicesRecylerview() {
        searchServicesAdapter = SearchServicesAdapter()
        binding.rvSearchServices.apply {
            adapter = searchServicesAdapter
            setHasFixedSize(true)
        }

    }

    private fun searchByServices(){

        val list= mutableListOf<String>()

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

}