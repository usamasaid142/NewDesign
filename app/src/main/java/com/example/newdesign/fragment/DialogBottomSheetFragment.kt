package com.example.newdesign.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newdesign.R
import com.example.newdesign.adapter.AutoCompleteAdapter
import com.example.newdesign.databinding.DialogBottomSheetfragmentBinding
import com.example.newdesign.model.CountryList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DialogBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var list:ArrayList<CountryList>

    private lateinit var binding:DialogBottomSheetfragmentBinding
    private lateinit var autoCompleteAdapter: AutoCompleteAdapter


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DialogBottomSheetfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillCountryList()
        autoCompleteAdapter= AutoCompleteAdapter(requireContext(),list)
        binding.itemCountry.tvSelectCountry.setAdapter(autoCompleteAdapter)

    }



    private fun fillCountryList(){

        list=ArrayList()
        list.add(CountryList(R.drawable.ic_eyeshow,"text1"))
//        list.add(CountryList(R.drawable.ic_eyehide,"text2"))
//        list.add(CountryList(R.drawable.ic_doctors,"text3"))
//        list.add(CountryList(R.drawable.ic_eyehide,"text4"))
//        list.add(CountryList(R.drawable.ic_eyehide,"text5"))



    }

}