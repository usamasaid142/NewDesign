package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.R

import com.example.newdesign.databinding.ItemLayoutcalendardateBinding
import com.example.newdesign.model.CalendarDateModel
import java.text.SimpleDateFormat
import java.util.*


class CalenderAdapter(private val selectDate:Action) :
    ListAdapter<CalendarDateModel, CalenderAdapter.ViewHolder>(DiffCallback()) {
    private val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    private var selectedItemPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            ItemLayoutcalendardateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calender = getItem(position)
        var date = calender.data
        holder.binding.tvCalendarDay.text = sdf.format(date)
        holder.binding.tvCalendarDate.text = date.date.toString()
        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.bindingAdapterPosition
            selectDate.onItemClick(calender.data)
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            holder.binding.cardCalendar.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_completeprofile)
          //  holder.binding.btnAll.setTextColor(Color.WHITE)
        } else {
            holder.binding.cardCalendar.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_buttonsearch)
           // holder.binding.btnAll.setTextColor(Color.parseColor("#262D70"))
        }



    }


    class ViewHolder(itemBinding: ItemLayoutcalendardateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutcalendardateBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<CalendarDateModel>() {
        override fun areItemsTheSame(oldItem: CalendarDateModel, newItem: CalendarDateModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CalendarDateModel, newItem: CalendarDateModel): Boolean {
            return true
        }
    }


    interface Action{
        fun onItemClick(date: Date)
    }

}