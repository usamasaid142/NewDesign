package com.example.newdesign.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.newdesign.R
import com.example.newdesign.adapter.AutoCompleteAdapter
import com.example.newdesign.databinding.DialogBottomSheetfragmentBinding
import com.example.newdesign.model.CountryList
import com.example.newdesign.utils.DateUtils.convertLongToDate
import com.example.newdesign.utils.DateUtils.toTimeDateString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        showDatepicker()
        autoCompleteAdapter= AutoCompleteAdapter(requireContext(),list)
        binding.itemCountry.tvSelectCountry.setAdapter(autoCompleteAdapter)
        binding.itemCountry.tvSelectCountry.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val pos=position+1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }



    private fun fillCountryList(){

        list=ArrayList()
        list.add(CountryList(R.drawable.ic_eyeshow,"text1"))
        list.add(CountryList(R.drawable.ic_eyehide,"text2"))
        list.add(CountryList(R.drawable.ic_doctors,"text3"))
        list.add(CountryList(R.drawable.ic_eyehide,"text4"))
        list.add(CountryList(R.drawable.ic_eyehide,"text5"))

    }


    private fun showDatepicker(){
        val datePicker=MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(requireActivity().supportFragmentManager,"date range" )

        datePicker.addOnPositiveButtonClickListener { date->
            val startDate=date
            val second=date
            Toast.makeText(requireContext(),"${convertLongToDate(startDate)} // ${second.toTimeDateString()}",Toast.LENGTH_LONG).show()
        }

    }


}