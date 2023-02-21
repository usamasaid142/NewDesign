package com.example.newdesign.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newdesign.R
import com.example.newdesign.databinding.ItemLayoutAppointmentsavailableBinding
import com.example.newdesign.databinding.ItemLayoutRvservicesBinding
import com.example.newdesign.model.ImageServices


class AppointmentsAvailableAdapter(private val selecttime:Action):ListAdapter<String,AppointmentsAvailableAdapter.ViewHolder>(DiffCallback()) {

    private var selectedItemPosition: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutAppointmentsavailableBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appoinments=getItem(position)

        holder.binding.tvTime.text=appoinments

        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.bindingAdapterPosition
            selecttime.onItemClick(appoinments)
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            holder.binding.layoutTime.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_chooselang_button)
              holder.binding.tvTime.setTextColor(Color.WHITE)
        } else {
            holder.binding.layoutTime.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_completeprofile)
            holder.binding.tvTime.setTextColor(Color.parseColor("#262D70"))
        }

    }


    class ViewHolder(itemBinding: ItemLayoutAppointmentsavailableBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutAppointmentsavailableBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }
    }

    interface Action{
        fun onItemClick(time: String)
    }



}