package com.example.newdesign.fragment.navbottom

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.newdesign.R
import com.example.newdesign.databinding.MySchedulefragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyScheduleFragment : Fragment() {

    private lateinit var binding:MySchedulefragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= MySchedulefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton(){

        binding.layoutUpcoming.setOnClickListener {
            binding.layoutUpcoming.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvUpcoming.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutFinished.setBackgroundResource( R.drawable.bg_help)
            binding.tvFinished.setTextColor(Color.parseColor("#262D70"))
            binding.layoutCanceled.setBackgroundResource(R.drawable.bg_help)
            binding.tvCanceled.setTextColor(Color.parseColor("#262D70"))
        }

        binding.layoutFinished.setOnClickListener {
           binding.layoutFinished.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvFinished.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutUpcoming.setBackgroundResource(R.drawable.bg_help)
            binding.tvUpcoming.setTextColor(Color.parseColor("#262D70"))
            binding.layoutCanceled.setBackgroundResource(R.drawable.bg_help)
            binding.tvCanceled.setTextColor(Color.parseColor("#262D70"))
        }

        binding.layoutCanceled.setOnClickListener {
            binding.layoutCanceled.setBackgroundResource(R.drawable.bg_button_sellector)
            binding.tvCanceled.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.layoutUpcoming.setBackgroundResource(R.drawable.bg_help)
            binding.tvUpcoming.setTextColor(Color.parseColor("#262D70"))
            binding.layoutFinished.setBackgroundResource(R.drawable.bg_help)
            binding.tvFinished.setTextColor(Color.parseColor("#262D70"))
        }

    }

}