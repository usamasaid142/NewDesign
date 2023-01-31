package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.databinding.ItemLayoutSpecialistBinding
import com.example.newdesign.databinding.ItemLayoutSubspecialistBinding
import com.example.newdesign.model.AreaData
import com.example.newdesign.model.CityData
import com.example.newdesign.model.SubSpecialistData


class GetAllAreaAdapter():ListAdapter<AreaData,GetAllAreaAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutSubspecialistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sp=getItem(position)

        holder.binding.radioSpecialist.text=sp.name

    }

    class ViewHolder(itemBinding: ItemLayoutSubspecialistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutSubspecialistBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<AreaData>() {
        override fun areItemsTheSame(oldItem: AreaData, newItem: AreaData): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: AreaData, newItem: AreaData): Boolean {
            return true
        }
    }




}