package com.example.newdesign.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.adapter.OnBoardingAdapter
import com.example.newdesign.databinding.OnboardingfragmentBinding
import com.example.newdesign.model.Onboarding


class OnboardingFragment : Fragment() {

    private lateinit var binding: OnboardingfragmentBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= OnboardingfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingAdapter= OnBoardingAdapter()
        initButton()
        onBoarding()
    }

    private fun initButton(){

        binding.next.setOnClickListener {

            if (binding.vpContainer.currentItem+1<onBoardingAdapter.itemCount){
                binding.vpContainer.currentItem+=1
            }else{
                findNavController().navigate(R.id.loginFragment)
            }
        }


        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

    }


    private fun onBoarding(){

        val list= mutableListOf<Onboarding>()

        list.add(
            Onboarding(
            R.drawable.ic_doctors,
            "text1",
                "djhjkhkksjlkjjll"
        )
        )

        list.add(Onboarding(
            R.drawable.ic_medical_care,
            "text2",
            "djhjkhkksjlkjjll"
        ))
        list.add(Onboarding(
            R.drawable.ic_online,
            "text1",

            "djhjkhkksjlkjjll"
        ))
        onBoardingAdapter.submitList(list)
        binding.vpContainer.adapter=onBoardingAdapter

    }

}