package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.databinding.ItemLayoutSpecialistBinding
import com.example.newdesign.databinding.ItemLayoutSubspecialistBinding
import com.example.newdesign.model.SeniorityLevelData
import com.example.newdesign.model.SubSpecialistData


class SeniorityLevelAdapter(private val selectLevel:SelectSeniorityLevel):ListAdapter<SeniorityLevelData,SeniorityLevelAdapter.ViewHolder>(DiffCallback()) {

    var selectedPosition=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutSubspecialistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sp=getItem(position)

        holder.binding.radioSpecialist.text=sp.name

        holder.binding.radioSpecialist.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectLevel.onItemSelectSeniorityLevel(sp)
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


    private class DiffCallback : DiffUtil.ItemCallback<SeniorityLevelData>() {
        override fun areItemsTheSame(oldItem: SeniorityLevelData, newItem: SeniorityLevelData): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: SeniorityLevelData, newItem: SeniorityLevelData): Boolean {
            return true
        }
    }


    interface SelectSeniorityLevel{
        fun onItemSelectSeniorityLevel(SeniorityLevel:SeniorityLevelData)
    }


}