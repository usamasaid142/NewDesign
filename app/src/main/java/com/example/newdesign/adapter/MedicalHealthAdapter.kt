package com.example.newdesign.adapter


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.newdesign.R
import com.example.newdesign.databinding.ItemLayoutHealthBinding
import com.example.newdesign.databinding.ItemLayoutMedicalrvservicesBinding
import com.example.newdesign.databinding.ItemLayoutRvservicesBinding
import com.example.newdesign.databinding.ItemlayoutOnboardingBinding
import com.example.newdesign.fragment.home.HomeFragmentDirections
import com.example.newdesign.model.ImageServices
import com.example.newdesign.model.Onboarding
import com.example.newdesign.model.healthy.ItemHealth


class MedicalHealthAdapter(val onclick:Action):ListAdapter<ItemHealth,MedicalHealthAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutHealthBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services=getItem(position)
        holder.binding.ivDoctorProfile.load("https://salamtechapi.azurewebsites.net/${services.logo}") {
            crossfade(true)
            placeholder(R.drawable.ic_medicalhistory)
            crossfade(1000)
            transformations(CircleCropTransformation())
        }
        holder.binding.tvDoctorName.text=services.name
         if(!services.healthEntityPhoneDtos.isNullOrEmpty()) {
             holder.binding.tvSubSpecialization.text = services.healthEntityPhoneDtos?.get(0)
         }
        holder.binding.tvAddress.text=services.address
        holder.binding.tvWaitingTime.text=services.fixedFee.toString()

         holder.binding.btnBooking.setOnClickListener {
             services.healthEntityPhoneDtos?.get(0)?.let { it1 -> onclick.onItemClick(it1) }
         }

    }




    class ViewHolder(itemBinding: ItemLayoutHealthBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutHealthBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<ItemHealth>() {
        override fun areItemsTheSame(oldItem: ItemHealth, newItem: ItemHealth): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ItemHealth, newItem: ItemHealth): Boolean {
            return true
        }
    }

interface Action{
    fun onItemClick(phone:String)
}


}