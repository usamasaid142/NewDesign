package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.R
import com.example.newdesign.databinding.ItemLayoutDoctorsBinding
import com.example.newdesign.fragment.home.SearchFragmentDirections
import com.example.newdesign.model.docotorsearch.Item


class SearchDoctorsAdapter(private val booking: Booking) :
    ListAdapter<Item, SearchDoctorsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            ItemLayoutDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services = getItem(position)

        holder.binding.apply {
            tvDoctorName.text = services.doctorName
            tvSpecialization.text = services.specialistName
            subSpecialization.text = services.subSpecialistName.toString()
            location.text = services.clinicDto?.Address
            waitingTime.text = " ${services.waitingTime}"
            Feesresult.text = services.feesFrom.toString()
        }
        holder.binding.btnBooking.setOnClickListener {
            services.clinicId?.let { it1 ->
                services.doctorId?.let { it2 ->
                    services.feesTo?.let { it3 ->
                        booking.onItemClick(
                            it1,
                            it2, it3
                        )
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
//            val action= services.doctorId?.let { it1 ->
//                SearchFragmentDirections.actionSearchFragmentToBookingAppointmentFragment(
//                    it1
//                )
//            }
//            if (action != null) {
//                it.findNavController().navigate(action)
//            }
            services.clinicId?.let { it1 -> services.doctorId?.let { it2 ->
                services.clinicDto?.Name?.let { it3 ->
                    booking.onItemClick(it1,
                        it2, it3
                    )
                }
            } }

        }


        holder.binding.ivDoctorProfile.load("https://salamtechapi.azurewebsites.net/${services.image}") {
            crossfade(true)
            crossfade(1000)
            transformations(CircleCropTransformation())
        }

    }


    class ViewHolder(itemBinding: ItemLayoutDoctorsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutDoctorsBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return true
        }
    }

    interface Booking {

        fun onItemClick(clinicId: Int, doctorId: Int, fess: Int)
        fun onItemClick(clinicId: Int, doctorId: Int,clinkname:String)
    }
}