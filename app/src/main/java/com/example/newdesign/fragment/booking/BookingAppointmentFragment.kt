package com.example.newdesign.fragment.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.ClinksDoctorsAdapter
import com.example.newdesign.adapter.SearchDoctorsAdapter
import com.example.newdesign.databinding.BookingAppointmentfragmentBinding
import com.example.newdesign.fragment.dialog.DialogBottomSheetFragmentArgs
import com.example.newdesign.fragment.home.SearchFragmentDirections
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingAppointmentFragment : Fragment(),ClinksDoctorsAdapter.Booking{

    private lateinit var binding:BookingAppointmentfragmentBinding
    private  val viewmodel: DialogBottomSheetViewmodel by viewModels()
    private val args: BookingAppointmentFragmentArgs by navArgs()
    private lateinit var clinksDoctorsAdapter: ClinksDoctorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= BookingAppointmentfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callBack()
        clinksRecylerview()
        initButton()
    }


    private fun initButton(){
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }
    private fun clinksRecylerview() {
        clinksDoctorsAdapter = ClinksDoctorsAdapter(this)
        binding.rvClinkDoctors.apply {
            adapter = clinksDoctorsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun callBack(){

        viewmodel.doctorClinkResponse.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading -> {
                    showprogtessbar()
                }
                is Resource.sucess -> {
                    hideprogressbar()
                    binding.AboutDoctor.text=it.data?.data?.doctorInfo
                    val name="${it.data?.data?.firstName}${it.data?.data?.middelName}${it.data?.data?.lastName}"
                    binding.tvDoctorName.text=name
                    binding.tvName.text=name
                     var subspecialist:String?=""
                    it.data?.data?.subSpecialistName?.forEach {
                        subspecialist= "$subspecialist $it,"
                    }
                    binding.tvSpecialization.text=subspecialist
                   clinksDoctorsAdapter.submitList(it.data?.data?.clinicDtos)
                   clinksDoctorsAdapter.notifyDataSetChanged()

                }
                is Resource.Error -> {
                    hideprogressbar()

                }

            }

        })
        viewmodel.getDoctorProfileByDoctorId(args.doctorId)
    }
    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
          }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
          }

    override fun onItemClick() {
        findNavController().navigate(R.id.bookAppointmentFragment)
    }

}