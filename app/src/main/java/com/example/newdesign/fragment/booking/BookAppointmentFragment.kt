package com.example.newdesign.fragment.booking

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.AppointmentsAvailableAdapter
import com.example.newdesign.adapter.SearchServicesAdapter
import com.example.newdesign.databinding.BookAppointmentfragmentBinding
import com.example.newdesign.fragment.home.HomeFragment
import com.example.newdesign.fragment.home.SearchFragmentDirections
import com.example.newdesign.model.CalendarDateModel
import com.example.newdesign.model.booking.AppointmentBooking
import com.example.newdesign.model.booking.BookingData
import com.example.newdesign.model.booking.BookingResponse
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@AndroidEntryPoint
class BookAppointmentFragment : Fragment(),SearchServicesAdapter.Action,AppointmentsAvailableAdapter.Action{


    private lateinit var binding:BookAppointmentfragmentBinding
    private lateinit var searchServicesAdapter: SearchServicesAdapter
    private lateinit var appointmentsAvailableAdapter: AppointmentsAvailableAdapter
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    val now = Calendar.getInstance(TimeZone.getTimeZone("CST"))
    val viewmodel: DialogBottomSheetViewmodel by viewModels()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    var formattedDate = ""
    var time: String? = null
    private var doctorWorkingDayTimeId: Int? = null
    private var fees: Int? = null
    private var intervalTime: Int? = null
    private var medicalExaminationTypeName: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=BookAppointmentfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        CalenderRecylerview()
        setUpCalendar()
        callBackGetClinicSchedualByClinicDayId()
        appointmentsAvailableRecylerview()
    }
    private fun initButton(){
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.bookingAppointmentFragment)
        }

        binding.ivNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivPrevious.setOnClickListener {

            cal.add(Calendar.MONTH, -1)
            setUpCalendar()
        }
    }
    private fun CalenderRecylerview() {
        searchServicesAdapter = SearchServicesAdapter(this)
        binding.rvSearchServices.apply {
            adapter = searchServicesAdapter
            setHasFixedSize(true)
        }
    }
    private fun appointmentsAvailableRecylerview() {
        appointmentsAvailableAdapter = AppointmentsAvailableAdapter(this)
        binding.rvAppointmentsAvailable.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = appointmentsAvailableAdapter
            setHasFixedSize(true)

        }

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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callBackGetClinicSchedualByClinicDayId() {
        viewmodel.bookingResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.sucess -> {

                    it.let {
                        val time = it?.data?.data
                        if (time != null) {
                            getAppointments(time as List<BookingData>)
                         //  binding.etChooseService.setText(time[0]?.medicalExaminationTypeName)
                        }

                    }

                }
                is Resource.Error -> {

//                   loginresponse.data?.let {
//                       Log.e("msg : ",it.message)
//
//                   }
                }

            }
        })
        sharedDataViewmodel.ClinicSchedualByClinicDayId.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewmodel.getClinicSchedualByClinicDayId(
                it.clinicId,
                it.dayId,
               it.medicalExaminationId,
                it.formattedDate
            )
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAppointments(appointmentTime:List<BookingData>) {
        val appointmentBookingList = mutableListOf<AppointmentBooking>()
        var timeFrom = ""
        var timeTo = ""
        var doctorWorkingDayTimeId = 0
        var intervaltime = 1
        var fees = 0
        var medicalExaminationTypeName = ""

        if (!appointmentTime.isNullOrEmpty()) {
            for (i in appointmentTime!!.indices) {

                timeFrom = appointmentTime[i]?.timeFrom!!
                timeTo = appointmentTime[i]?.timeTo!!
                intervaltime = appointmentTime[i]?.timeInterval!!
                doctorWorkingDayTimeId = appointmentTime[i]?.schedualId!!
                fees = appointmentTime[i]?.fees!!
                medicalExaminationTypeName = appointmentTime[i]?.medicalExaminationTypeName!!

                if (!timeFrom.isNullOrEmpty() && !timeTo.isNullOrEmpty()) {


                    var timefrom = LocalTime.parse(timeFrom)
                    val timeto = LocalTime.parse(timeTo)
                    val diff: Duration = Duration.between(timefrom, timeto)
                    var hours: Long = diff.toHours()
                    val numbersofpatient = if (hours.toInt()==0){
                        60 / intervaltime
                    }else {
                        ((hours * 60) / intervaltime).toInt()
                    }
                    appointmentBookingList.add(
                        AppointmentBooking(
                            timefrom.toString(),
                            intervaltime,
                            doctorWorkingDayTimeId,
                            fees,
                            medicalExaminationTypeName
                        )
                    )

                    for (i in 1 until numbersofpatient) {
                        timefrom = timefrom.plusMinutes(intervaltime.toLong())
                        appointmentBookingList.add(
                            AppointmentBooking(
                                timefrom.toString(),
                                intervaltime,
                                doctorWorkingDayTimeId,
                                fees,
                                medicalExaminationTypeName
                            )
                        )

                    }

                    Log.e(
                        "timeFrom ",
                        timefrom.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                    )
                    Log.e(
                        "timeto ",
                        timeto.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                    )

                } else {
                    Toast.makeText(
                        requireContext(),
                        "there is no Appointments available",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("timeFrom is empty ", timeFrom.toString())
                }


            }

        } else {
            Toast.makeText(
                requireContext(),
                "there is no Appointments available",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (appointmentBookingList.isNullOrEmpty()){
            binding.ivCalenderviw.visibility=View.VISIBLE
        }else{
            binding.ivCalenderviw.visibility=View.GONE
            binding.rvAppointmentsAvailable.visibility=View.VISIBLE
            appointmentsAvailableAdapter.submitList(appointmentBookingList)
            appointmentsAvailableAdapter.notifyDataSetChanged()
        }


    }

    override fun onItemClick(
        time: String,
        intervalTime: Int,
        Fess: Int,
        doctorWorkingDayTimeId: Int,
        medicalExaminationTypeName: String
    ) {
        this.time = time
        this.doctorWorkingDayTimeId = doctorWorkingDayTimeId
        this.fees = Fess
        this.medicalExaminationTypeName = medicalExaminationTypeName
        this.intervalTime = intervalTime
    }

}