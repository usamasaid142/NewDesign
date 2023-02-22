package com.example.newdesign.adapter


import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.R
import com.example.newdesign.databinding.ItemLayoutAppointmentsavailableBinding
import com.example.newdesign.model.booking.AppointmentBooking
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AppointmentsAvailableAdapter(private val selecttime: Action) :
    ListAdapter<AppointmentBooking, AppointmentsAvailableAdapter.ViewHolder>(DiffCallback()) {

    private var selectedItemPosition: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = ItemLayoutAppointmentsavailableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appoinments = getItem(position)

        for (i in appoinments.time!!.indices) {

                 var timefrom = LocalTime.parse(appoinments.time?.get(i))
                 val time = timefrom.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                 holder.binding.tvTime.text = time
                 timefrom = timefrom.plusHours(2)


        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.bindingAdapterPosition
            selecttime.onItemClick(timefrom.toString())
            notifyDataSetChanged()

             }
        }
        if (selectedItemPosition == position) {
            holder.binding.layoutTime.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_chooselang_button)
            holder.binding.tvTime.setTextColor(Color.WHITE)
        } else {
            holder.binding.layoutTime.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_completeprofile)
            holder.binding.tvTime.setTextColor(Color.parseColor("#262D70"))

        }

    }


    class ViewHolder(itemBinding: ItemLayoutAppointmentsavailableBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutAppointmentsavailableBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<AppointmentBooking>() {
        override fun areItemsTheSame(
            oldItem: AppointmentBooking,
            newItem: AppointmentBooking
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AppointmentBooking,
            newItem: AppointmentBooking
        ): Boolean {
            return true
        }
    }

    interface Action {
        fun onItemClick(time: String)
    }


}