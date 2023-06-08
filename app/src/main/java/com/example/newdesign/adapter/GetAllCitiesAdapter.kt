package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.databinding.ItemLayoutSpecialistBinding
import com.example.newdesign.databinding.ItemLayoutSubspecialistBinding
import com.example.newdesign.model.CityData
import com.example.newdesign.model.SpecialistData
import com.example.newdesign.model.SubSpecialistData
import com.example.newdesign.utils.localization.DefaultLocaleHelper


class GetAllCitiesAdapter(private val selectCity: SelectCity):ListAdapter<CityData,GetAllCitiesAdapter.ViewHolder>(DiffCallback()) {
    var selectedPosition=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutSubspecialistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sp=getItem(position)

        if (DefaultLocaleHelper.getInstance(holder.binding.radioSpecialist.context).getCurrentLocale()=="AR"){
            holder.binding.radioSpecialist.text=sp.nameAr
        }else{
            holder.binding.radioSpecialist.text=sp.name

        }

        holder.binding.radioSpecialist.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectCity.onItemSelected(sp)
            }
        })
        holder.binding.radioSpecialist.setOnClickListener { view ->
            selectedPosition = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }

        holder.binding.radioSpecialist.isChecked = selectedPosition == position

    }

    class ViewHolder(itemBinding: ItemLayoutSubspecialistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutSubspecialistBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<CityData>() {
        override fun areItemsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return true
        }
    }

    interface SelectCity{
        fun onItemSelected(city: CityData)
    }


}