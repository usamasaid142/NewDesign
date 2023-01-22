package com.example.newdesign.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newdesign.R

import com.example.newdesign.databinding.ItemLayoutSearchBinding


class SearchServicesAdapter() :
    ListAdapter<String, SearchServicesAdapter.ViewHolder>(DiffCallback()) {

    private var selectedItemPosition: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            ItemLayoutSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services = getItem(position)

        holder.binding.btnAll.text = services
        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            holder.binding.btnAll.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_buttonsearch)
            holder.binding.btnAll.setTextColor(Color.WHITE)
        } else {
            holder.binding.btnAll.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_completeprofile)
            holder.binding.btnAll.setTextColor(Color.parseColor("#262D70"))
        }

    }


    class ViewHolder(itemBinding: ItemLayoutSearchBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutSearchBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }
    }
}