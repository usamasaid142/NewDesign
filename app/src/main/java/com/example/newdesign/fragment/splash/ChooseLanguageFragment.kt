package com.example.newdesign.fragment.splash

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.ChooseLanguagefragmentBinding
import com.example.newdesign.utils.Constans.Language
import com.example.newdesign.utils.DateUtils
import com.example.newdesign.utils.LocaleHelper
import com.example.newdesign.utils.SpUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ChooseLanguageFragment : Fragment() {

    private lateinit var binding: ChooseLanguagefragmentBinding
    var lang: String? = null
    @Inject
    lateinit var sp: SpUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ChooseLanguagefragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseLanuage()

        binding.btnChoose.setOnClickListener {

            if (sp.getUserLang(Language).isNullOrEmpty()) {
                Toast.makeText(requireContext(),getString(R.string.choose_language), Toast.LENGTH_SHORT).show()
            } else {

                setLocale(lang!!)
                requireActivity().finish()
                startActivity(requireActivity().intent)
            }
        }


    }


    private fun chooseLanuage() {

        binding.radio.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_english -> {
                    // Pirates are the best
                    lang = "En"
                    sp.saveUserLang(Language, lang!!)
                    DateUtils.setLanguage(lang!!)


                }
                R.id.radio_arabic -> {

                    lang = "AR"
                    sp.saveUserLang(Language, lang!!)
                    DateUtils.setLanguage(lang!!)

                }
            }


        }


    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("deprecation")
    private fun updateResourcesLegacy(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    fun setLocale(lang: String): Context? {
        return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            updateResources(
                requireContext(),
                lang
            )
        } else updateResourcesLegacy(
            requireContext(),
            lang
        )
    }


}