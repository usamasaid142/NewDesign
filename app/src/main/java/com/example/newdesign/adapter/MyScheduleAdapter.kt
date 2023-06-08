package com.example.newdesign.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.databinding.ItemLayoutScheduleBinding
import com.example.newdesign.model.scheduling.DataAppointment
import java.text.SimpleDateFormat
import java.util.*


class MyScheduleAdapter(private val schedule: ActionSchedule) :
    ListAdapter<DataAppointment, MyScheduleAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            ItemLayoutScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services = getItem(position)

        holder.binding.apply {
            tvDoctorName.text = services.doctorName
            tvSpecialization.text = services.specialistName
            Service.text=services.medicalExaminationTypeName
            location.text = services.clinicAddress
            waitingTime.text = " ${DateParsing(services.appointmentDate.toString())}"
            Feesresult.text = services.fees.toString()
        }
        holder.binding.btnMoreAction.setOnClickListener {

            services.appointmentDate?.let { it1 ->
                services.doctorId?.let { it2 ->
                    services.clinicId?.let { it3 ->
                        services.clinicName?.let { it4 ->
                            services.medicalExaminationTypeId?.let { it5 ->
                                services.appointmentId?.let { it6 ->
                                    schedule.onItemClick(
                                        it3, it2, it4,
                                        it1, it5, it6
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        holder.binding.ivDoctorProfile.load("https://salamtechapi.azurewebsites.net/${services.doctorImage}") {
            crossfade(true)
            crossfade(1000)
            transformations(CircleCropTransformation())
        }

    }


    class ViewHolder(itemBinding: ItemLayoutScheduleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutScheduleBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<DataAppointment>() {
        override fun areItemsTheSame(oldItem: DataAppointment, newItem: DataAppointment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataAppointment, newItem: DataAppointment): Boolean {
            return true
        }
    }

    interface ActionSchedule {
        fun onItemClick(clinicId: Int, doctorId: Int,clinkname:String,formaterDate:String,medicalExaminationTypeId:Int,AppointmentId:Int)
    }

    private fun DateParsing(dateParsing:String):String{
        var day=""
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy  hh:mm a", Locale.getDefault())
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = dateParsing.let { format.parse(it) }
            day = sdf.format(date)

        }catch (e:Exception){
            Log.e("",e.localizedMessage)
        }
        return day
    }
}