package com.example.newdesign.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.FilterfragmentBinding
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private lateinit var binding: FilterfragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FilterfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton() {

        collabsedFiller(
            binding.layoutSpecializationHeader,
            binding.layoutSpecializationDetails,
            binding.ivCollabsarrowdown,
            binding.ivArrowRight
        )
        collabsedFiller(
            binding.layoutLocationHeader,
            binding.layoutLocationDetails,
            binding.ivHideLocationarrowRight,
            binding.ivLocationarrowRight
        )
        collabsedFiller(
            binding.layoutFeesHeader,
            binding.layoutFeesDetails,
            binding.ivHideFeesarrowRight,
            binding.ivFeesarrowRight
        )
        collabsedFiller(
            binding.layoutGenderHeader,
            binding.layoutGenderDetails,
            binding.ivHideGenderarrowRight,
            binding.ivGenderarrowRight
        )

        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

    }


    private fun collabsedFiller(
        view: View,
        hideview: View,
        image: ShapeableImageView,
        imagearrowup: ShapeableImageView
    ) {
        var isVisible = true
        view.setOnClickListener {
            if (isVisible) {
                hideview.visibility = View.GONE
                image.visibility = View.VISIBLE
                imagearrowup.visibility = View.GONE
                isVisible = false
            } else {
                hideview.visibility = View.VISIBLE
                image.visibility = View.GONE
                imagearrowup.visibility = View.VISIBLE
                isVisible = true
            }
        }


    }

}