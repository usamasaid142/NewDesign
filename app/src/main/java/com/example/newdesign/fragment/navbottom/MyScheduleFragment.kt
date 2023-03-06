package com.example.newdesign.fragment.navbottom

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.MyScheduleAdapter
import com.example.newdesign.adapter.SearchDoctorsAdapter
import com.example.newdesign.databinding.MySchedulefragmentBinding
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MyScheduleFragment : Fragment() ,MyScheduleAdapter.ActionSchedule{

    private lateinit var binding:MySchedulefragmentBinding
    private lateinit var myScheduleAdapter: MyScheduleAdapter
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    val viewmodel: DialogBottomSheetViewmodel by viewModels()
    val now = Calendar.getInstance(TimeZone.getTimeZone("CST"))
    var formattedDate = ""
    var sechdule=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= MySchedulefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
            this.timeZone = TimeZone.getTimeZone("CST")
        }
        formattedDate = formatter.format(now.time)
        initButton()
        callback()
        doctorsRecylerview()
    }

    private fun doctorsRecylerview() {
        myScheduleAdapter = MyScheduleAdapter(this)
        binding.rvPatientAppointment.apply {
            adapter = myScheduleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
    private fun initButton(){

        binding.layoutUpcoming.setOnClickListener {
            binding.layoutUpcoming.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvUpcoming.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutFinished.setBackgroundResource( R.drawable.bg_help)
            binding.tvFinished.setTextColor(Color.parseColor("#262D70"))
            binding.layoutCanceled.setBackgroundResource(R.drawable.bg_help)
            binding.tvCanceled.setTextColor(Color.parseColor("#262D70"))
            sechdule="Upcoming"
            callback()

        }

        binding.layoutFinished.setOnClickListener {
           binding.layoutFinished.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvFinished.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutUpcoming.setBackgroundResource(R.drawable.bg_help)
            binding.tvUpcoming.setTextColor(Color.parseColor("#262D70"))
            binding.layoutCanceled.setBackgroundResource(R.drawable.bg_help)
            binding.tvCanceled.setTextColor(Color.parseColor("#262D70"))
            sechdule="Finished"
            callback()
        }

        binding.layoutCanceled.setOnClickListener {
            binding.layoutCanceled.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvCanceled.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutUpcoming.setBackgroundResource(R.drawable.bg_help)
            binding.tvUpcoming.setTextColor(Color.parseColor("#262D70"))
            binding.layoutFinished.setBackgroundResource(R.drawable.bg_help)
            binding.tvFinished.setTextColor(Color.parseColor("#262D70"))
            sechdule="Canceled"
            callback()
        }

    }

    private fun callback(){
        viewmodel.patientsAppointmentesResponse.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading -> {
                    showprogtessbar()
                }
                is Resource.sucess ->{
                    hideprogressbar()
                    when(sechdule){
                        "Upcoming"->{
                            val upcominglist=it.data?.data?.filter {
                                it?.appointmentDate!! >formattedDate
                            }
                            Log.e("true",upcominglist.toString())
                            myScheduleAdapter.submitList(upcominglist)
                            myScheduleAdapter.notifyDataSetChanged()
                        }
                        "Canceled"->{
                            val Canceledlist=it.data?.data?.filter {
                                it?.isCancel==true
                            }
                            Log.e("true",Canceledlist.toString())
                            myScheduleAdapter.submitList(Canceledlist)
                            myScheduleAdapter.notifyDataSetChanged()
                        }else ->{
                        myScheduleAdapter.submitList(it.data?.data)
                        myScheduleAdapter.notifyDataSetChanged()
                    }

                    }

                }
                is Resource.Error ->{
                    hideprogressbar()
                }

            }
        })
        sharedDataViewmodel.ClinicSchedualByClinicDayId.observe(viewLifecycleOwner, Observer {

        })
        viewmodel.getPatientAppointmentes(null)

    }
    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE

    }

    override fun onItemClick(clinicId: Int, doctorId: Int, fess: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(clinicId: Int, doctorId: Int, clinkname: String) {
        TODO("Not yet implemented")
    }

}