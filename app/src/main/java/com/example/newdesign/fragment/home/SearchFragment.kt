package com.example.newdesign.fragment.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.SearchDoctorsAdapter
import com.example.newdesign.adapter.SearchServicesAdapter
import com.example.newdesign.databinding.SearchfragmentBinding
import com.example.newdesign.model.CalendarDateModel
import com.example.newdesign.model.booking.AppointmentBooking
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchServicesAdapter.Action, SearchDoctorsAdapter.Booking {

    private lateinit var binding: SearchfragmentBinding
    private lateinit var searchServicesAdapter: SearchServicesAdapter
    private lateinit var searchDoctorsAdapter: SearchDoctorsAdapter
    private lateinit var bottomsheetbeahavoir: BottomSheetBehavior<ConstraintLayout>

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    val now = Calendar.getInstance(TimeZone.getTimeZone("CST"))
    var specialistId = 0
    var doctorId = 0
    var feesTo = 0
    var seniortyLevelId = 0
    var cityId = 0
    var areaId = 0
    var genderId = 0
    var formattedDate = ""
    var sub_SpecialistId = mutableListOf<Int>()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    val viewmodel: DialogBottomSheetViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = SearchfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomsheetbeahavoir =
            BottomSheetBehavior.from(binding.layoutBottomsheetpersistant.filterBottomsheet)
        bottomsheetbeahavoir.state = BottomSheetBehavior.STATE_HIDDEN


        servicesRecylerview()
        doctorsRecylerview()
        initButtonCollabsedFiller()
        bindFields()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
            this.timeZone = TimeZone.getTimeZone("CST")
        }
        formattedDate = formatter.format(now.time)
        val doctorssearchRequset = DoctorSearchRequest(
            0,
            formattedDate,
            0,
            "",
            feesFrom = 0,
            feesTo = 0,
            genderId,
            10,
            HomeFragment.instance?.medicalExaminatioId!!,
            0,
            0,
            specialistId
        )
        viewmodel.searchDoctors(doctorssearchRequset)
        doctorsSearch()
        callBackGetClinicSchedualByClinicDayId()
        setUpCalendar()
        initButton()
    }

    private fun initButton() {

        binding.ivNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivPrevious.setOnClickListener {

            cal.add(Calendar.MONTH, -1)
            setUpCalendar()
        }

        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.layoutFilter.setOnClickListener {
            bottomsheetbeahavoir.state =
                if (bottomsheetbeahavoir.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED
        }

        binding.layoutBottomsheetpersistant.layoutChooseSpecialization.setOnClickListener {
            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("Specialist")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.layoutChooseSubSpecialization.setOnClickListener {

            if (binding.layoutBottomsheetpersistant.etSpecialization.text?.toString()
                    ?.isEmpty()!!
            ) {
                Toast.makeText(requireContext(), "Chose specialist first ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("SubSpecialist")
                findNavController().navigate(action)
            }
        }

        binding.layoutBottomsheetpersistant.layoutChooseSeniorityLevel.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("SeniorityLevel")
            findNavController().navigate(action)
        }
        binding.layoutBottomsheetpersistant.layoutChooseCity.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("AllCity")
            findNavController().navigate(action)
        }
        binding.layoutBottomsheetpersistant.layoutChooseArea.setOnClickListener {
            if (binding.layoutBottomsheetpersistant.etChooseCity.text?.toString()?.isEmpty()!!) {
                Toast.makeText(requireContext(), "Chose City first ", Toast.LENGTH_SHORT).show()
            } else {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("AllArea")
                findNavController().navigate(action)
            }
        }
        binding.layoutBottomsheetpersistant.layoutChooseGender.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("gender")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.btnApply.setOnClickListener {

            val doctorssearchRequset = DoctorSearchRequest(
                areaId,
                formattedDate,
                cityId,
                binding.etSearch.text.toString(),
                feesFrom = 0,
                feesTo = 0,
                genderId,
                10,
                1,
                seniortyLevelId,
                0,
                specialistId,
                sub_SpecialistId
            )
            viewmodel.searchDoctors(doctorssearchRequset)
        }

    }

    private fun servicesRecylerview() {
        searchServicesAdapter = SearchServicesAdapter(this)
        binding.rvSearchServices.apply {
            adapter = searchServicesAdapter
            setHasFixedSize(true)
        }
    }

    private fun doctorsRecylerview() {
        searchDoctorsAdapter = SearchDoctorsAdapter(this)
        binding.rvDoctors.apply {
            adapter = searchDoctorsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
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


    private fun bindFields() {
        sharedDataViewmodel.specialication.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etSpecialization.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etSpecialization.toString().isEmpty()) {
                specialistId = it.id
            } else specialistId = 0
        }
        sharedDataViewmodel.subSpecialication.observe(viewLifecycleOwner) {
            var name = ""
            for (i in it.indices) {

                name += "${it[i].name}-"
            }
            binding.layoutBottomsheetpersistant.etSubSpecialization.setText(name)
            if (!binding.layoutBottomsheetpersistant.etSubSpecialization.toString().isEmpty()) {
                for (i in it.indices) {
                    sub_SpecialistId.add(it[i].id)
                }

            }
        }

        sharedDataViewmodel.seniorityLevelData.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etSeniority.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etSeniority.toString().isEmpty()) {
                seniortyLevelId = it.id
            } else seniortyLevelId = 0
        }
        sharedDataViewmodel.getCity.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etChooseCity.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etChooseCity.toString().isEmpty()) {
                cityId = it.id
            } else cityId = 0
        }

        sharedDataViewmodel.getArea.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etChooseArea.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etChooseArea.toString().isEmpty()) {
                areaId = it.id
            } else areaId = 0
        }

        sharedDataViewmodel.chooseGender.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etChooseGender.setText(it.gender)
            if (!binding.layoutBottomsheetpersistant.etChooseGender.toString().isEmpty()) {
                genderId = it.id
            } else genderId = 0

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun doctorsSearch() {

        viewmodel.docorsResponse.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    it.let {
                        bottomsheetbeahavoir.state = BottomSheetBehavior.STATE_HIDDEN
                        searchDoctorsAdapter.submitList(it.data?.data?.items)
                        searchDoctorsAdapter.notifyDataSetChanged()
                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
//                   loginresponse.data?.let {
//                       Log.e("msg : ",it.message)
//
//                   }
                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callBackGetClinicSchedualByClinicDayId() {
        viewmodel.bookingResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                    showprogtessbar()
                }
                is Resource.sucess -> {
                    hideprogressbar()
                    it.let {
                        val time = it?.data?.data
                        if (time != null) {

                            val patientAppointmentRequest=PatientAppointmentRequest(doctorId,0,formattedDate,false)
                            val action =
                                SearchFragmentDirections.actionSearchFragmentToDialogClinkBookingFragment(
                                    patientAppointmentRequest,it.data)
                            findNavController().navigate(action)

                        }

                    }

                }
                is Resource.Error -> {
                    hideprogressbar()
//                   loginresponse.data?.let {
//                       Log.e("msg : ",it.message)
//
//                   }
                }

            }
        })
    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.layoutBottomsheetpersistant.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
        binding.layoutBottomsheetpersistant.progressBar.visibility = View.GONE

    }

    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        binding.tvDate.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            if (monthCalendar.time.time >= now.time.time) {

                calendarList.add(CalendarDateModel(monthCalendar.time))
            }
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        searchServicesAdapter.submitList(calendarList)
    }

    override fun onItemClick(date: Date) {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        formattedDate = formatter.format(date)
        val doctorssearchRequset = DoctorSearchRequest(
            areaId,
            formattedDate,
            cityId,
            binding.etSearch.text.toString(),
            feesFrom = 0,
            feesTo = 0,
            genderId,
            10,
            HomeFragment.instance?.medicalExaminatioId!!,
            0,
            0,
            specialistId
        )
        viewmodel.searchDoctors(doctorssearchRequset)
    }

    override fun onItemClick(clinicId: Int,doctorId:Int,fessTo:Int) {
        this.doctorId=doctorId
        this.feesTo=feesTo
        viewmodel.getClinicSchedualByClinicDayId(
            clinicId,
            1,
            HomeFragment.instance?.medicalExaminatioId!!,
            formattedDate
        )
    }

}