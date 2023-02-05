package com.example.newdesign.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.R
import com.example.newdesign.databinding.ItemLayoutDoctorsBinding

import com.example.newdesign.databinding.ItemLayoutSearchBinding
import com.example.newdesign.model.docotorsearch.DoctorSearchResponseItem


class SearchDoctorsAdapter() :
    ListAdapter<DoctorSearchResponseItem, SearchDoctorsAdapter.ViewHolder>(DiffCallback()) {

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
            location.text= services.healthEntities?.get(position)?.address ?: ""
            waitingTime.text=" ${services.waitingTime}"
            Feesresult.text=services.feesFrom.toString()
        }

    }


    class ViewHolder(itemBinding: ItemLayoutDoctorsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutDoctorsBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<DoctorSearchResponseItem>() {
        override fun areItemsTheSame(oldItem: DoctorSearchResponseItem, newItem: DoctorSearchResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DoctorSearchResponseItem, newItem: DoctorSearchResponseItem): Boolean {
            return true
        }
    }
}