package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.newdesign.databinding.ItemLayoutDoctorsBinding
import com.example.newdesign.model.docotorsearch.Item


class SearchDoctorsAdapter() :
    ListAdapter<Item, SearchDoctorsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            ItemLayoutDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services = getItem(position)

        holder.binding.apply {
            tvDoctorName.text=services.doctorName
            tvSpecialization.text=services.specialistName
            subSpecialization.text= services.subSpecialistName.toString()
            location.text= services.clinicDto?.Address
            waitingTime.text=" ${services.waitingTime}"
            Feesresult.text=services.feesFrom.toString()
        }

//        holder.itemView.apply {
//            Glide.with(this)
//                .load("https://salamtechapi.azurewebsites.net/${services.image}")
//                .into(holder.binding.ivDoctorProfile)
//        }
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
}