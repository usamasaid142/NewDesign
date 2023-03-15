package com.example.newdesign.fragment.dialog

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.AppointmentsAvailableAdapter
import com.example.newdesign.databinding.DialogClinkBookingfragmentBinding
import com.example.newdesign.fragment.loginandforgetpassword.LoginFragment
import com.example.newdesign.model.booking.AppointmentBooking
import com.example.newdesign.model.booking.AppointmentDetailBooking
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@AndroidEntryPoint
class DialogClinkBookingFragment : BottomSheetDialogFragment(),
    AppointmentsAvailableAdapter.Action {

    private lateinit var binding: DialogClinkBookingfragmentBinding
    private lateinit var appointmentsAvailableAdapter: AppointmentsAvailableAdapter
    private val args: DialogClinkBookingFragmentArgs by navArgs()
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    var time: String? = null
    private var doctorWorkingDayTimeId: Int? = null
    private var fees: Int? = null
    private var intervalTime: Int? = null
    private var medicalExaminationTypeName: String? = null
    private var formattedDate: String? = null
    val appointmentBookingList = mutableListOf<AppointmentBooking>()
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

    }
    companion object{
        var instance: DialogClinkBookingFragment?=null

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogClinkBookingfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instance=this
        getAppointments()
        appointmentsAvailableRecylerview()
        callBack()
        initButton()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButton() {
        binding.btnConfirm.setOnClickListener {
            if (time.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "choose appointment time first",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val date: Date =
                    SimpleDateFormat("yyyy-MM-dd").parse(args.appointmentrequest.AppointmentDate)
                val startHour: Date = time?.let { it1 -> SimpleDateFormat("hh:mm").parse(it1) }!!
                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val total = date.time + startHour.time
                formattedDate = formatter.format(total)
                val patientAppointmentRequest = PatientAppointmentRequest(
                    args.appointmentrequest.DoctorId,
                    doctorWorkingDayTimeId,
                    formattedDate,
                    args.appointmentrequest.IsBook
                )
                viewmodel.createPatientAppointment(patientAppointmentRequest)
            }
        }

    }

    private fun appointmentsAvailableRecylerview() {
        appointmentsAvailableAdapter = AppointmentsAvailableAdapter(this)
        binding.rvAppointmentsAvailable.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = appointmentsAvailableAdapter
            setHasFixedSize(true)
            appointmentsAvailableAdapter.submitList(appointmentBookingList)
        }

    }

    private fun callBack() {
        viewmodel.patientAppointmentResponse.observe(viewLifecycleOwner, Observer {

            when (it) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    val appointmentDetailBooking= args.appointmentrequest.DoctorId?.let { it1 ->
                        AppointmentDetailBooking(it.data?.data?.doctorName,
                            it1,formattedDate,intervalTime,doctorWorkingDayTimeId,fees,medicalExaminationTypeName,it.data?.data?.doctorDto?.specialistName )
                    }
                   val action=DialogClinkBookingFragmentDirections.actionDialogClinkBookingFragmentToAppointmentDetailsFragment(appointmentDetailBooking
                  ,true )
                    findNavController().navigate(action)
                }

                is Resource.Error -> {
                    hideprogressbar()
                    Toast.makeText(requireContext(),"${it.message}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAppointments() {
        var timeFrom = ""
        var timeTo = ""
        var doctorWorkingDayTimeId = 0
        var intervaltime = 1
        var fees = 0
        var medicalExaminationTypeName = ""
        val appointmentTime = args.appointments?.data
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
                            medicalExaminationTypeName,
                            appointmentTime[0]?.bookedAppointments as List<String>?
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
                                medicalExaminationTypeName,
                                appointmentTime[0]?.bookedAppointments as List<String>?
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
    }}

}