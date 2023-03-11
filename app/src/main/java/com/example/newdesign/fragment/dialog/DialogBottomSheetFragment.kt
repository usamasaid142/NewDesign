package com.example.newdesign.fragment.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.*
import com.example.newdesign.databinding.DialogBottomSheetfragmentBinding
import com.example.newdesign.model.*
import com.example.newdesign.model.register.ChooseGender
import com.example.newdesign.utils.DataBinding.displayToastText
import com.example.newdesign.utils.DateUtils.convertLongToDate
import com.example.newdesign.utils.DateUtils.toTimeDateString
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DialogBottomSheetFragment : BottomSheetDialogFragment(), SpecialistAdapter.SelectSpecialist,
    SubSpecialistAdapter.SelectSubSpecialist, SeniorityLevelAdapter.SelectSeniorityLevel,
    GetAllCitiesAdapter.SelectCity, GetAllAreaAdapter.SelectArea {


    private lateinit var binding: DialogBottomSheetfragmentBinding
    val viewmodel: DialogBottomSheetViewmodel by viewModels()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private val args: DialogBottomSheetFragmentArgs by navArgs()
    private lateinit var counteriesAdapter: CounteriesAdapter
    private lateinit var specialistAdapter: SpecialistAdapter
    private lateinit var subSpecialistAdapter: SubSpecialistAdapter
    private lateinit var seniorityLevelAdapter: SeniorityLevelAdapter
    private lateinit var getAllCitiesAdapter: GetAllCitiesAdapter
    private lateinit var getAllAreaAdapter: GetAllAreaAdapter

    //  private lateinit var autoCompleteAdapter: AutoCompleteAdapter


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogBottomSheetfragmentBinding.inflate(layoutInflater, container, false)
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
        setupresyclerviewSubSpecialist()
        setupresyclerviewSeniorityLevel()
        setupresyclerviewGetAllCities()
        setupresyclerviewGetAllArea()
        initButton()
        chooseGender()

    }

    fun setupresyclerview() {
        counteriesAdapter = CounteriesAdapter()
        binding.itemNationality.rvNationality.apply {
            adapter = counteriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
            counteriesAdapter.notifyDataSetChanged()

        }
    }

    fun setupresyclerviewSpecialist() {
        specialistAdapter = SpecialistAdapter(this)
        binding.itemSpecialist.rvSpecialist.apply {
            adapter = specialistAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
        }
    }

    fun setupresyclerviewSubSpecialist() {
        subSpecialistAdapter = SubSpecialistAdapter(this)
        binding.itemSubSpecialist.rvSubspecialist.apply {
            adapter = subSpecialistAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
        }
    }

    fun setupresyclerviewSeniorityLevel() {
        seniorityLevelAdapter = SeniorityLevelAdapter(this)
        binding.itemSeniorityLevel.rvSeniorityLevel.apply {
            adapter = seniorityLevelAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
        }
    }

    fun setupresyclerviewGetAllCities() {
        getAllCitiesAdapter = GetAllCitiesAdapter(this)
        binding.itemAllcities.rvAllCities.apply {
            adapter = getAllCitiesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
        }
    }

    fun setupresyclerviewGetAllArea() {
        getAllAreaAdapter = GetAllAreaAdapter(this)
        binding.itemAllArea.rvAllArea.apply {
            adapter = getAllAreaAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
        }
    }


    private fun initButton() {
        binding.btnCalling.setOnClickListener {
            call()
            dismiss()
        }
        binding.btnDone.setOnClickListener {
            dismiss()
        }
        binding.itemGender.btnDone.setOnClickListener {
            dismiss()
        }

    }



    private fun showDatepicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(requireActivity().supportFragmentManager.beginTransaction(), "date range")

        datePicker.addOnPositiveButtonClickListener { date ->
            val startDate = date
            val second = date
            Toast.makeText(
                requireContext(),
                "${convertLongToDate(startDate)} // ${second.toTimeDateString()}",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    private fun changeViews(selectviews: String) {

        when (selectviews) {
            "date" -> {
                binding.layoutDateOfbirth.visibility = View.VISIBLE
                binding.btnDone.visibility = View.GONE
                binding.tvAllSpecial.visibility = View.GONE
            }
            "help" -> {
                binding.tvCallus.visibility = View.VISIBLE
                binding.ivCall.visibility = View.VISIBLE
                binding.btnCalling.visibility = View.VISIBLE
                binding.btnDone.visibility = View.GONE
                binding.tvAllSpecial.visibility = View.GONE
            }
            "gender" -> {
                binding.layoutGender.visibility = View.VISIBLE
                binding.btnDone.visibility = View.GONE
                binding.tvAllSpecial.visibility = View.GONE
            }
            "lang" -> {
                binding.layoutLanguage.visibility = View.VISIBLE
                binding.btnDone.visibility = View.GONE
                binding.tvAllSpecial.visibility = View.GONE
            }
            "signout" -> {
                binding.layoutSignOut.visibility = View.VISIBLE
                binding.btnDone.visibility = View.GONE
                binding.tvAllSpecial.visibility = View.GONE
            }
            "Nationality" -> {
                binding.layoutNationality.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_your_nationality)
                callBack()
            }
            "Specialist" -> {
                binding.layoutSpecialist.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_specialization)
                callBackSpecialist()

            }
            "services" -> {
                binding.layoutSpecialist.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_specialization)
                binding.btnDone.visibility = View.GONE
                callBackSpecialist()

            }
            "SubSpecialist" -> {
                binding.layoutSubSpecialist.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_sub_specialization)
                callBackSubSpecialist()
            }
            "SeniorityLevel" -> {
                binding.layoutSeniorityLevel.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_seniority_level)
                callBackSeniorityLevel()
            }
            "AllCity" -> {
                binding.layoutAllCities.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_city)
                callBackGetAllCities()
            }
            "AllArea" -> {
                binding.layoutAllArea.visibility = View.VISIBLE
                binding.tvAllSpecial.text = getString(R.string.choose_area)
                callBackGetAllArea()
            }
        }

    }

    private fun callBack() {
        viewmodel.nationalityResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {
                        counteriesAdapter.submitList(it)
                        counteriesAdapter.notifyDataSetChanged()
                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        viewmodel.getCountries()
    }

    private fun callBackSpecialist() {
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
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        viewmodel.getSpecialist()
    }

    private fun callBackSubSpecialist() {
        viewmodel.subSpecialistResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {
                        subSpecialistAdapter.submitList(it)
                        subSpecialistAdapter.notifyDataSetChanged()
                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        sharedDataViewmodel.specialication.observe(viewLifecycleOwner) {
            viewmodel.getSubSpecialist(it.id)
        }

    }

    private fun callBackSeniorityLevel() {
        viewmodel.seniorityLevelResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {
                        seniorityLevelAdapter.submitList(it)
                        seniorityLevelAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        viewmodel.getSeniorityLevel()
    }

    private fun callBackGetAllCities() {
        viewmodel.allCitiesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {
                        getAllCitiesAdapter.submitList(it)
                        getAllCitiesAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        viewmodel.getAllCities()
    }

    private fun callBackGetAllArea() {
        viewmodel.allAreasByCityIdResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data.let {
                        getAllAreaAdapter.submitList(it)
                        getAllAreaAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
        sharedDataViewmodel.getCity.observe(viewLifecycleOwner) {
            viewmodel.getAreasByCityId(it.id)
        }

    }


    private fun showprogtessbar() {
        binding.itemNationality.progressBar.visibility = View.VISIBLE
        binding.itemSpecialist.progressBar.visibility = View.VISIBLE
        binding.itemSubSpecialist.progressBar.visibility = View.VISIBLE
        binding.itemSeniorityLevel.progressBar.visibility = View.VISIBLE
        binding.itemAllcities.progressBar.visibility = View.VISIBLE
        binding.itemAllArea.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.itemNationality.progressBar.visibility = View.GONE
        binding.itemSpecialist.progressBar.visibility = View.GONE
        binding.itemSubSpecialist.progressBar.visibility = View.GONE
        binding.itemSeniorityLevel.progressBar.visibility = View.GONE
        binding.itemAllcities.progressBar.visibility = View.GONE
        binding.itemAllArea.progressBar.visibility = View.GONE
    }

    override fun onItemSelected(specialist: SpecialistData) {
        if (args.changeview == "services") {
            sharedDataViewmodel.getspecialication(specialist)
            findNavController().navigate(R.id.searchFragment)
        } else {
            sharedDataViewmodel.getspecialication(specialist)
        }

    }

    override fun onSelectSubcialist(listofsubSpecialist: MutableList<SubSpecialistData>) {
        sharedDataViewmodel.getSubSpecialication(listofsubSpecialist)
    }

    override fun onItemSelectSeniorityLevel(SeniorityLevel: SeniorityLevelData) {
        sharedDataViewmodel.getSeniorityLevelData(SeniorityLevel)
    }

    override fun onItemSelected(city: CityData) {
        sharedDataViewmodel.getCity(city)
    }

    override fun onSelectArea(Area: AreaData) {
        sharedDataViewmodel.getArea(Area)
    }


//    private fun updateUi(it: List<DataCountry>?) {
//        if (it.isNullOrEmpty()) {
//            binding.itemNationality.crdView.visibility = View.VISIBLE
//        } else {
//            binding.itemNationality.crdView.visibility = View.GONE
//        }
//    }

    private fun chooseGender() {

        binding.itemGender.radio.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_male -> {
                    val chooseGender = ChooseGender("Male", 1)
                    sharedDataViewmodel.getGender(chooseGender)
                }
                R.id.radio_female -> {
                    val chooseGender = ChooseGender("Female", 2)
                    sharedDataViewmodel.getGender(chooseGender)
                }
            }
        }

    }

    fun call() {

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "17143", null))
                this.startActivity(intent)
    }
}