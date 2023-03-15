package com.example.newdesign.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.newdesign.R
import com.example.newdesign.databinding.ItemLayoutExaminationtypeBinding
import com.example.newdesign.databinding.ItemLayoutMedicalexaminationtypeBinding
import com.example.newdesign.databinding.ItemLayoutRvservicesBinding
import com.example.newdesign.model.ImageServices
import com.example.newdesign.model.MedicalExamination


class MedicalEaxminationTypeAdapter(private val onServicesClick:Action ):ListAdapter<ImageServices,MedicalEaxminationTypeAdapter.ViewHolder>(DiffCallback()) {

    var selectedPosition=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutMedicalexaminationtypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services=getItem(position)
        holder.binding.ivExaminationtype.load("https://salamtechapi.azurewebsites.net/${services.image_onboarding}") {
            crossfade(true)
            crossfade(1000)
            transformations(CircleCropTransformation())
        }

        holder.binding.tvExaminationtype.text=services.textService

        holder.binding.chExaminationtype.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onServicesClick.onItemClick(services)
                holder.binding.tvExaminationtype.setTextColor(Color.parseColor("#2B2979"))
            }else{
                holder.binding.tvExaminationtype.setTextColor(Color.parseColor("#B7B7B7"))
            }
        })
        holder.binding.chExaminationtype.setOnClickListener { view ->
            selectedPosition = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }

        holder.binding.chExaminationtype.isChecked = selectedPosition == position

    }


    class ViewHolder(itemBinding: ItemLayoutMedicalexaminationtypeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutMedicalexaminationtypeBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<ImageServices>() {
        override fun areItemsTheSame(oldItem: ImageServices, newItem: ImageServices): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ImageServices, newItem: ImageServices): Boolean {
            return true
        }
    }

    interface Action{
        fun onItemClick(examinationTypeId: ImageServices)
    }



}