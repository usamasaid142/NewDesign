package com.example.newdesign.fragment.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.AppointmentsAvailableAdapter
import com.example.newdesign.databinding.DialogClinkBookingfragmentBinding
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@AndroidEntryPoint
class DialogClinkBookingFragment : BottomSheetDialogFragment(),
    AppointmentsAvailableAdapter.Action {

    private lateinit var binding: DialogClinkBookingfragmentBinding
    private lateinit var appointmentsAvailableAdapter: AppointmentsAvailableAdapter
    private val args: DialogClinkBookingFragmentArgs by navArgs()
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    var time: String? = null

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

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
        appointmentsAvailableRecylerview()
        callBack()
        initButton()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButton() {
        binding.btnConfirm.setOnClickListener {

            val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(args.appointmentrequest.AppointmentDate)
            val startHour: Date = SimpleDateFormat("hh:mm a").parse(time)
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
           val total = date.time + startHour.time
            val formattedDate = formatter.format(total)
            val patientAppointmentRequest = PatientAppointmentRequest(
                6129,
                45353,
                args.appointmentrequest.Fees,
                "2023-02-21T23:10:00Z",
                args.appointmentrequest.Comment,
                args.appointmentrequest.IsBook
            )
            viewmodel.createPatientAppointment(patientAppointmentRequest)
        }


    }

    private fun appointmentsAvailableRecylerview() {
        appointmentsAvailableAdapter = AppointmentsAvailableAdapter(this)
        binding.rvAppointmentsAvailable.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = appointmentsAvailableAdapter
            setHasFixedSize(true)
            args.appoinments?.let {
                appointmentsAvailableAdapter.submitList(it.toList())
            }

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
                    findNavController().navigate(R.id.appointmentDetailsFragment)

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
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE

    }

    override fun onItemClick(time: String) {
        this.time = time
    }

}