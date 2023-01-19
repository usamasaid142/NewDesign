package com.example.newdesign.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newdesign.databinding.ItemLayoutSliderBinding
import com.example.newdesign.model.HomeAdsData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class ImageVideoAdapter : ListAdapter<HomeAdsData, ImageVideoAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemLayoutSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)


        if (model.IsVideo) {
            val videoid = model.VideoLink.split("=").toTypedArray()
            holder.binding.youtubePlayerView.visibility = View.VISIBLE
            holder.binding.ivHome.visibility = View.GONE
            holder.binding.youtubePlayerView.apply {
                this.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                        val videoId = videoid[1]
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }

        } else {
            holder.binding.ivHome.visibility = View.VISIBLE
            holder.binding.youtubePlayerView.visibility = View.GONE
            holder.binding.ivHome.apply {
                Glide
                    .with(this.context)
                    .load("https://salamtechapi.azurewebsites.net${model.VideoLink}")
                    .into(this)
            }

        }


    }

    class ViewHolder(itemBinding: ItemLayoutSliderBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutSliderBinding = itemBinding
    }


    private class DiffCallback : DiffUtil.ItemCallback<HomeAdsData>() {
        override fun areItemsTheSame(oldItem: HomeAdsData, newItem: HomeAdsData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeAdsData, newItem: HomeAdsData): Boolean {
            return true
        }
    }


}