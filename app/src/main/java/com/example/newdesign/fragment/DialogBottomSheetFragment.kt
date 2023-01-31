package com.example.newdesign.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.AutoCompleteAdapter
import com.example.newdesign.adapter.CounteriesAdapter
import com.example.newdesign.adapter.SpecialistAdapter
import com.example.newdesign.databinding.DialogBottomSheetfragmentBinding
import com.example.newdesign.model.CountryList
import com.example.newdesign.model.register.DataCountry
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.DateUtils.convertLongToDate
import com.example.newdesign.utils.DateUtils.toTimeDateString
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.RegisterViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

@AndroidEntryPoint
class DialogBottomSheetFragment : BottomSheetDialogFragment() {

    //private lateinit var list:ArrayList<CountryList>

    private lateinit var binding:DialogBottomSheetfragmentBinding
    val viewmodel: DialogBottomSheetViewmodel by viewModels()
    private val args:DialogBottomSheetFragmentArgs by navArgs()
    private lateinit var counteriesAdapter: CounteriesAdapter
    private lateinit var specialistAdapter:SpecialistAdapter
  //  private lateinit var autoCompleteAdapter: AutoCompleteAdapter


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
        args.changeview?.let { changeViews(it) }
      //  fillCountryList()
        //showDatepicker()
//        autoCompleteAdapter= AutoCompleteAdapter(requireContext(),list)
//        binding.itemCountry.tvSelectCountry.setAdapter(autoCompleteAdapter)
//        binding.itemCountry.tvSelectCountry.onItemSelectedListener=object :OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                val pos=position+1
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//        }
        setupresyclerview()
        setupresyclerviewSpecialist()
        initButton()

    }

    fun setupresyclerview() {
        counteriesAdapter = CounteriesAdapter()
        binding.itemNationality.rvNationality.apply {
            adapter = counteriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL){})
            counteriesAdapter.notifyDataSetChanged()

        }
    }

    fun setupresyclerviewSpecialist() {
        specialistAdapter = SpecialistAdapter()
        binding.itemSpecialist.rvSpecialist.apply {
            adapter = specialistAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL){})
        }
    }





    private fun initButton(){
        binding.btnCalling.setOnClickListener {
            dismiss()
        }
    }

//    private fun fillCountryList(){
//
//        list=ArrayList()
//        list.add(CountryList(R.drawable.ic_eyeshow,"text1"))
//        list.add(CountryList(R.drawable.ic_eyehide,"text2"))
//        list.add(CountryList(R.drawable.ic_doctors,"text3"))
//        list.add(CountryList(R.drawable.ic_eyehide,"text4"))
//        list.add(CountryList(R.drawable.ic_eyehide,"text5"))
//
//    }


    private fun showDatepicker(){
        val datePicker=MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(requireActivity().supportFragmentManager.beginTransaction(),"date range" )

        datePicker.addOnPositiveButtonClickListener { date->
            val startDate=date
            val second=date
            Toast.makeText(requireContext(),"${convertLongToDate(startDate)} // ${second.toTimeDateString()}",Toast.LENGTH_LONG).show()
        }

    }


    private fun changeViews(selectviews:String){

        when(selectviews){
            "date"->{
                binding.layoutDateOfbirth.visibility=View.VISIBLE
            }
            "help"->{
                binding.tvCallus.visibility=View.VISIBLE
                binding.ivCall.visibility=View.VISIBLE
                binding.btnCalling.visibility=View.VISIBLE
            }
            "gender"->{
                binding.layoutGender.visibility=View.VISIBLE
            }
            "Nationality"->{
                binding.layoutNationality.visibility=View.VISIBLE
                callBack()
            }
            "Specialist"->{
                binding.layoutSpecialist.visibility=View.VISIBLE
                callBackSpecialist()

            }
        }

    }

    private fun callBack(){
        viewmodel.nationalityResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {


                        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.N){
                            val list= mutableListOf<DataCountry>()
                            for (i in 0..10){
                                list.add(it!![i])
                            }
                            counteriesAdapter.submitList(list)
                            counteriesAdapter.notifyDataSetChanged()
                        }else{
                            counteriesAdapter.submitList(it)
                            counteriesAdapter.notifyDataSetChanged()
                        }


                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                 Toast.makeText(requireContext(),it.data.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        viewmodel.getCountries()
    }

    private fun callBackSpecialist(){
        viewmodel.specialistResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {
                        specialistAdapter.submitList(it)
                        specialistAdapter.notifyDataSetChanged()
                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(),it.data.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        viewmodel.getSpecialist()
    }

    private fun showprogtessbar() {
        binding.itemNationality.progressBar.visibility = View.VISIBLE
        binding.itemSpecialist.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.itemNationality.progressBar.visibility = View.GONE
        binding.itemSpecialist.progressBar.visibility = View.GONE
    }

//    private fun updateUi(it: List<DataCountry>?) {
//        if (it.isNullOrEmpty()) {
//            binding.itemNationality.crdView.visibility = View.VISIBLE
//        } else {
//            binding.itemNationality.crdView.visibility = View.GONE
//        }
//    }


}