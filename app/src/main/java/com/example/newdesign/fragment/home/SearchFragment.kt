package com.example.newdesign.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.SearchDoctorsAdapter
import com.example.newdesign.adapter.SearchServicesAdapter
import com.example.newdesign.databinding.SearchfragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchfragmentBinding
    private lateinit var searchServicesAdapter: SearchServicesAdapter
    private lateinit var searchDoctorsAdapter: SearchDoctorsAdapter
    private lateinit var bottomsheetbeahavoir: BottomSheetBehavior<ConstraintLayout>
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

        bottomsheetbeahavoir = BottomSheetBehavior.from(binding.layoutBottomsheetpersistant.filterBottomsheet)
        bottomsheetbeahavoir?.state = BottomSheetBehavior.STATE_HIDDEN

        initButton()
        servicesRecylerview()
        searchByServices()
        doctorsRecylerview()
        doctorSearches()
        initButtonCollabsedFiller()
    }

    private fun initButton() {
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.layoutFilter.setOnClickListener {
            bottomsheetbeahavoir?.state =
                if (bottomsheetbeahavoir?.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED
        }

        binding.layoutBottomsheetpersistant.layoutChooseSpecialization.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("Specialist")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.layoutChooseSubSpecialization.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("SubSpecialist")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.layoutChooseSeniorityLevel.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("SeniorityLevel")
            findNavController().navigate(action)
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
            layoutManager = LinearLayoutManager(requireContext())
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

    private fun initButtonCollabsedFiller() {

        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutSpecializationHeader,
            binding.layoutBottomsheetpersistant.layoutSpecializationDetails,
            binding.layoutBottomsheetpersistant.layoutIvCollabsarrowdown,
            binding.layoutBottomsheetpersistant.ivArrowRight
        )
        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutLocationHeader,
            binding.layoutBottomsheetpersistant.layoutLocationDetails,
            binding.layoutBottomsheetpersistant.layoutIvHideLocationarrowRight,
            binding.layoutBottomsheetpersistant.ivLocationarrowRight
        )
        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutFeesHeader,
            binding.layoutBottomsheetpersistant.layoutFeesDetails,
            binding.layoutBottomsheetpersistant.layoutIvHideFeesarrowRight,
            binding.layoutBottomsheetpersistant.ivFeesarrowRight
        )
        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutGenderHeader,
            binding.layoutBottomsheetpersistant.layoutGenderDetails,
            binding.layoutBottomsheetpersistant.layoutIvHideGenderarrowRight,
            binding.layoutBottomsheetpersistant.ivGenderarrowRight
        )

        binding.layoutBottomsheetpersistant.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

    }


    private fun collabsedFiller(
        view: View,
        hideview: View,
        layout: View,
        imagearrowup: ShapeableImageView
    ) {
        var isVisible = true
        view.setOnClickListener {
            if (isVisible) {
                hideview.visibility = View.GONE
                layout.visibility = View.VISIBLE
                imagearrowup.visibility = View.GONE
                isVisible = false
            } else {
                hideview.visibility = View.VISIBLE
                layout.visibility = View.GONE
                imagearrowup.visibility = View.VISIBLE
                isVisible = true
            }
        }


    }

}