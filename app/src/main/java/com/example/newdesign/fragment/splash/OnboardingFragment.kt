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
                if ( binding.vpContainer.currentItem==2){
                    binding.next.text="Lets start"
                }
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
            R.drawable.ic_doctor_patient,
                getString(R.string.salamtak_booking),
                getString(R.string.screen_booking_text)
        )
        )

        list.add(Onboarding(
            R.drawable.ic_connect_salamtak,
            getString(R.string.salamtak_booking),
            getString(R.string.screen_booking_text)
        ))
        list.add(Onboarding(
            R.drawable.ic_patient,
            getString(R.string.salamtak_booking),
            getString(R.string.screen_booking_text)
        ))
        onBoardingAdapter.submitList(list)
        binding.vpContainer.adapter=onBoardingAdapter

    }

}