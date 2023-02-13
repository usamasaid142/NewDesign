package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newdesign.databinding.ItemLayoutRvservicesBinding
import com.example.newdesign.model.ImageServices


class ServicesAdapter(private val onServicesClick:Action ):ListAdapter<ImageServices,ServicesAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view=ItemLayoutRvservicesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val services=getItem(position)
        Glide.with(holder.binding.ivServices)
            .load(services.image_onboarding)
            .into(holder.binding.ivServices)

        holder.binding.tvServiceName.text=services.textService

        holder.binding.cardview.setOnClickListener {
            services.Id?.let { it1 -> onServicesClick.onItemClick(it1) }
        }

    }


    class ViewHolder(itemBinding: ItemLayoutRvservicesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutRvservicesBinding = itemBinding
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
        fun onItemClick(servicesId: Int)
    }



}