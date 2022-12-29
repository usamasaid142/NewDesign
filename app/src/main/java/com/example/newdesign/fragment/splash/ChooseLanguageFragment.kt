package com.example.newdesign.fragment.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.ChooseLanguagefragmentBinding
import java.util.*


class ChooseLanguageFragment : Fragment() {



    private lateinit var binding: ChooseLanguagefragmentBinding
    private lateinit var lang:String
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= ChooseLanguagefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->  } {
//            chooseLanuage(checkedId)
//        }
        chooseLanuage()

        binding.btnChoose.setOnClickListener {
            findNavController().navigate(R.id.onboardingFragment)
        }

    }


    private fun chooseLanuage(){

        binding.radio.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.radio_english ->
                {
                        // Pirates are the best
                        lang="EN"
//                    setLocal(requireActivity(), lang!!)
//                    requireActivity().finish()
//                    startActivity(requireActivity().intent)


                    }
                R.id.radio_arabic ->
                     {
                        // Ninjas rule
                        //  findNavController().navigate(R.id.onboardingFragment)
                        lang="AR"
//                        setLocal(requireActivity(), lang!!)
//                         requireActivity().finish()
//                         startActivity(requireActivity().intent)


                     }
            }


        }

    }

    private fun setLocal(context: Activity, lang:String){

        val local= Locale(lang)
        Locale.setDefault(local)
        val resource =context.resources
        val config=resource.configuration
        config.setLocale(local)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//           context.createConfigurationContext(config);
//        } else {
//         context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//        }

        resource.updateConfiguration(config, resource.displayMetrics)

    }


}