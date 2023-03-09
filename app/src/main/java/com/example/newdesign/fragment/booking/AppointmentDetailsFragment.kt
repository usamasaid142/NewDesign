package com.example.newdesign.fragment.booking

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newdesign.R
import com.example.newdesign.databinding.AppointmentDetailsfragmentBinding
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentDetailsFragment : Fragment() {

    private lateinit var binding:AppointmentDetailsfragmentBinding
    private val args: AppointmentDetailsFragmentArgs by navArgs()
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= AppointmentDetailsfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDataToViews()
        initButton()
        callBack()
    }

    private fun initButton(){
        binding.ivArrow.setOnClickListener{
            if (args.status==true) {
                findNavController().navigate(R.id.searchFragment)
            }else{
                findNavController().navigate(R.id.myScheduleFragment)
            }
        }

        binding.btnConfirm.setOnClickListener {
            viewmodel.createPatientAppointment(
                PatientAppointmentRequest(args.confirmappointments?.doctorId,
                    args.confirmappointments?.DoctorWorkingDayTimeId,args.confirmappointments?.formattedDate
                    ,true)
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindDataToViews(){
        binding.tvDoctorName.text=args.confirmappointments?.doctorName
        binding.tvSpecialization.text=args.confirmappointments?.SpecialistName
        binding.tvDate.text=args.confirmappointments?.formattedDate
        binding.Service.text=args.confirmappointments?.MedicalExaminationTypeName
        binding.WaitingTime.text=args.confirmappointments?.timeInterval.toString()
        binding.TotalFees.text=args.confirmappointments?.fees.toString()

    }

    private fun callBack(){

      viewmodel?.patientAppointmentResponse?.observe(viewLifecycleOwner,
            Observer {
                when (it) {

                    is Resource.Loading -> {
                        showprogtessbar()
                    }

                    is Resource.sucess -> {
                        hideprogressbar()
                        Toast.makeText(requireContext()," booking Successfully",Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.myScheduleFragment)
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


}