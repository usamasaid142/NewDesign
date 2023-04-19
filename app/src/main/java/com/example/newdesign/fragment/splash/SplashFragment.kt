package com.example.newdesign.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.SplashfragmentBinding
import com.example.newdesign.utils.DateUtils


class SplashFragment : Fragment() {

    private lateinit var binding: SplashfragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = SplashfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            //doSomethingHere()
            if( DateUtils.getLanguage().isNullOrEmpty()){
                findNavController().navigate(R.id.chooseLanguageFragment)
            }else if (!DateUtils.getLanguageFromMore().isNullOrEmpty()){
                findNavController().navigate(R.id.moreFragment)
                 DateUtils.setLanguageFromMore("")
            }else if(!DateUtils.getToken().isNullOrEmpty()){
                Log.e("lang",DateUtils.getLanguage().toString())
                findNavController().navigate(R.id.homeFragment)
            }else{
                findNavController().navigate(R.id.onboardingFragment)
            }
        }, 2000)

    }


}