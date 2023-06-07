package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.databinding.ItemLayoutSubspecialistBinding
import com.example.newdesign.model.SubSpecialistData
import com.example.newdesign.model.profile.DataMedicineAllergy


class MedicineAllergyAdapter(private val selectMedicineAllergy:SelectMedicineAllergy):ListAdapter<DataMedicineAllergy,MedicineAllergyAdapter.ViewHolder>(DiffCallback()) {

    private val medicineAllergyList= mutableListOf<DataMedicineAllergy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutSubspecialistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sp=getItem(position)

        holder.binding.radioSpecialist.text=sp.name
          holder.binding.radioSpecialist.setOnCheckedChangeListener { buttonView, isChecked ->
              if (isChecked) {
                  medicineAllergyList.add(sp)
              } else {
                  medicineAllergyList.remove(sp)
              }
              selectMedicineAllergy.onSelectMedicineAllergy(medicineAllergyList)
          }

    }

    class ViewHolder(itemBinding: ItemLayoutSubspecialistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutSubspecialistBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<DataMedicineAllergy>() {
        override fun areItemsTheSame(oldItem: DataMedicineAllergy, newItem: DataMedicineAllergy): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: DataMedicineAllergy, newItem: DataMedicineAllergy): Boolean {
            return true
        }
    }


    interface SelectMedicineAllergy{
        fun onSelectMedicineAllergy(listofMedicineAllergy:MutableList<DataMedicineAllergy>)
    }




}