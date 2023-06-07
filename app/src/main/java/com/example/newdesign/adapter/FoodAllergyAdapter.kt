package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.databinding.ItemLayoutSubspecialistBinding
import com.example.newdesign.model.SubSpecialistData
import com.example.newdesign.model.profile.DataFoodAllergy
import com.example.newdesign.model.profile.DataMedicineAllergy


class FoodAllergyAdapter(private val selectFoodAllergy:SelectFoodAllergy):ListAdapter<DataFoodAllergy,FoodAllergyAdapter.ViewHolder>(DiffCallback()) {

    private val foodAllergyList= mutableListOf<DataFoodAllergy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutSubspecialistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sp=getItem(position)

        holder.binding.radioSpecialist.text=sp.name
          holder.binding.radioSpecialist.setOnCheckedChangeListener { buttonView, isChecked ->
              if (isChecked) {
                  foodAllergyList.add(sp)
              } else {
                  foodAllergyList.remove(sp)
              }
              selectFoodAllergy.onSelectFoodAllergy(foodAllergyList)
          }

    }

    class ViewHolder(itemBinding: ItemLayoutSubspecialistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutSubspecialistBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<DataFoodAllergy>() {
        override fun areItemsTheSame(oldItem: DataFoodAllergy, newItem: DataFoodAllergy): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: DataFoodAllergy, newItem: DataFoodAllergy): Boolean {
            return true
        }
    }


    interface SelectFoodAllergy{
        fun onSelectFoodAllergy(listofFoodAllergy:MutableList<DataFoodAllergy>)
    }




}