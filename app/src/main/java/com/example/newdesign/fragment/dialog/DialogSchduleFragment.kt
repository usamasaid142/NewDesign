package com.example.newdesign.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newdesign.R
import com.example.newdesign.databinding.DialogSchdulefragmentBinding
import com.example.newdesign.model.booking.clink.GetDoctorClinksResponse
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogSchduleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSchdulefragmentBinding
    private  val viewmodel: DialogBottomSheetViewmodel by viewModels()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private val args:DialogSchduleFragmentArgs by navArgs()
    private lateinit var getDoctorClinksResponse: GetDoctorClinksResponse
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogSchdulefragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showView()
        callBack()
        initButton()
    }

    private fun initButton(){

        binding.tvCancelAppointment.setOnClickListener {

            binding.layoutCanceled.visibility=View.GONE
            binding.layoutCancelAppointments.visibility=View.VISIBLE

        }

        binding.tvEdit.setOnClickListener {

            val action=DialogSchduleFragmentDirections.actionDialogSchduleFragmentToBookAppointmentFragment(getDoctorClinksResponse,"Edit Appointment")
            findNavController().navigate(action)

        }

        binding.btnReschedule.setOnClickListener {

            val action=DialogSchduleFragmentDirections.actionDialogSchduleFragmentToBookAppointmentFragment(getDoctorClinksResponse,"Reschedule")
            findNavController().navigate(action)
        }
        binding.tvReBookingAppointment.setOnClickListener {

            val action=DialogSchduleFragmentDirections.actionDialogSchduleFragmentToBookAppointmentFragment(getDoctorClinksResponse,"Re-Booking Appointment")
            findNavController().navigate(action)
        }

        binding.btnConfirm.setOnClickListener {
            callBackCancelAppointment()
        }

        binding.tvHelp.setOnClickListener {
            val action=DialogSchduleFragmentDirections.actionDialogSchduleFragmentToDialogBottomSheetFragment("help")
            findNavController().navigate(action)
        }



    }

    private fun callBack(){

        viewmodel.doctorClinkResponse.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading -> {

                }
                is Resource.sucess -> {

                    getDoctorClinksResponse= GetDoctorClinksResponse(it.data?.data,it.message)
                }
                is Resource.Error -> {


                }

            }

        })
        sharedDataViewmodel.doctorId.observe(viewLifecycleOwner, Observer {
            viewmodel.getDoctorProfileByDoctorId(it)
        })

    }

    private fun callBackCancelAppointment(){
        viewmodel.cancelPatientResponse.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
                is Resource.sucess -> {
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),"Appointment Canceled Successfully",Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility=View.GONE
                }

            }
        })
        viewmodel.cancelPatientAppointment(args.appointmentId)
    }

    private fun showView(){
        when(args.schedule){
            "Upcoming"->{
             binding.layoutCanceled.visibility=View.VISIBLE
            }
            "Finished"->{
                binding.layoutReBookingAppointment.visibility=View.VISIBLE
            }
            else->{
             binding.layoutCanceled.visibility=View.VISIBLE
            }
        }
    }

}