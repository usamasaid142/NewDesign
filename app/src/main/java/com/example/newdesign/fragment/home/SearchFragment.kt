package com.example.newdesign.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.SearchDoctorsAdapter
import com.example.newdesign.adapter.SearchServicesAdapter
import com.example.newdesign.databinding.SearchfragmentBinding
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchfragmentBinding
    private lateinit var searchServicesAdapter: SearchServicesAdapter
    private lateinit var searchDoctorsAdapter: SearchDoctorsAdapter
    private lateinit var bottomsheetbeahavoir: BottomSheetBehavior<ConstraintLayout>
    var SpecialistId=0
    var SeniortyLevelId=0
    var CityId=0
    var AreaId=0
    var GenderId=0
    var sub_SpecialistId= mutableListOf<Int>()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    val viewmodel:DialogBottomSheetViewmodel by viewModels()
    private var partMap: Map<String, Any> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = SearchfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomsheetbeahavoir =
            BottomSheetBehavior.from(binding.layoutBottomsheetpersistant.filterBottomsheet)
        bottomsheetbeahavoir?.state = BottomSheetBehavior.STATE_HIDDEN

        initButton()
        servicesRecylerview()
        searchByServices()
        doctorsRecylerview()
        initButtonCollabsedFiller()
        bindFields()
    }

    private fun initButton() {
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.layoutFilter.setOnClickListener {
            bottomsheetbeahavoir?.state =
                if (bottomsheetbeahavoir?.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED
        }

        binding.layoutBottomsheetpersistant.layoutChooseSpecialization.setOnClickListener {
            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("Specialist")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.layoutChooseSubSpecialization.setOnClickListener {

            if (binding.layoutBottomsheetpersistant.etSpecialization.text?.toString()
                    ?.isEmpty()!!
            ) {
                Toast.makeText(requireContext(), "Chose specialist first ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("SubSpecialist")
                findNavController().navigate(action)
            }
        }

        binding.layoutBottomsheetpersistant.layoutChooseSeniorityLevel.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("SeniorityLevel")
            findNavController().navigate(action)
        }
        binding.layoutBottomsheetpersistant.layoutChooseCity.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("AllCity")
            findNavController().navigate(action)
        }
        binding.layoutBottomsheetpersistant.layoutChooseArea.setOnClickListener {
            if (binding.layoutBottomsheetpersistant.etChooseCity.text?.toString()?.isEmpty()!!) {
                Toast.makeText(requireContext(), "Chose City first ", Toast.LENGTH_SHORT).show()
            } else {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("AllArea")
                findNavController().navigate(action)
            }
        }
        binding.layoutBottomsheetpersistant.layoutChooseGender.setOnClickListener {

            val action =
                SearchFragmentDirections.actionSearchFragmentToDialogBottomSheetFragment("gender")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.btnApply.setOnClickListener {
            doctorsSearch()
        }



    }

    private fun servicesRecylerview() {
        searchServicesAdapter = SearchServicesAdapter()
        binding.rvSearchServices.apply {
            adapter = searchServicesAdapter
            setHasFixedSize(true)
        }
    }

    private fun doctorsRecylerview() {
        searchDoctorsAdapter = SearchDoctorsAdapter()
        binding.rvDoctors.apply {
            adapter = searchDoctorsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun searchByServices() {

        val list = mutableListOf<String>()

        list.add(
            getString(R.string.Clinic_Booking),
        )
        list.add(
            getString(R.string.Home_Visit)
        )
        list.add(
            getString(R.string.Chat),

            )
        list.add(
            getString(R.string.Call),
        )
        list.add(
            getString(R.string.Video_Call)
        )
        searchServicesAdapter.submitList(list)
        searchServicesAdapter.notifyDataSetChanged()

    }

    private fun doctorSearches() {

        val list = mutableListOf<String>()

        list.add(
            getString(R.string.osama),
        )
        list.add(
            getString(R.string.said)
        )

        //searchDoctorsAdapter.submitList(list)


    }

    private fun initButtonCollabsedFiller() {

        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutSpecializationHeader,
            binding.layoutBottomsheetpersistant.layoutSpecializationDetails,
            binding.layoutBottomsheetpersistant.layoutIvCollabsarrowdown,
            binding.layoutBottomsheetpersistant.ivArrowRight
        )
        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutLocationHeader,
            binding.layoutBottomsheetpersistant.layoutLocationDetails,
            binding.layoutBottomsheetpersistant.layoutIvHideLocationarrowRight,
            binding.layoutBottomsheetpersistant.ivLocationarrowRight
        )
        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutFeesHeader,
            binding.layoutBottomsheetpersistant.layoutFeesDetails,
            binding.layoutBottomsheetpersistant.layoutIvHideFeesarrowRight,
            binding.layoutBottomsheetpersistant.ivFeesarrowRight
        )
        collabsedFiller(
            binding.layoutBottomsheetpersistant.layoutGenderHeader,
            binding.layoutBottomsheetpersistant.layoutGenderDetails,
            binding.layoutBottomsheetpersistant.layoutIvHideGenderarrowRight,
            binding.layoutBottomsheetpersistant.ivGenderarrowRight
        )

        binding.layoutBottomsheetpersistant.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

    }


    private fun collabsedFiller(
        view: View,
        hideview: View,
        layout: View,
        imagearrowup: ShapeableImageView
    ) {
        var isVisible = true
        view.setOnClickListener {
            if (isVisible) {
                hideview.visibility = View.GONE
                layout.visibility = View.VISIBLE
                imagearrowup.visibility = View.GONE
                isVisible = false
            } else {
                hideview.visibility = View.VISIBLE
                layout.visibility = View.GONE
                imagearrowup.visibility = View.VISIBLE
                isVisible = true
            }
        }


    }


    private fun bindFields() {
        sharedDataViewmodel.specialication.observe(viewLifecycleOwner, Observer {
            binding.layoutBottomsheetpersistant.etSpecialization.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etSpecialization.toString().isEmpty()){
               // partMap=partMap + mapOf("SpecialistId" to it.id)
                SpecialistId=it.id
            }else SpecialistId=0
        })
        sharedDataViewmodel.subSpecialication.observe(viewLifecycleOwner) {
            var name = ""
            for (i in it.indices) {

                name += "${it[i].name}-"
            }
            binding.layoutBottomsheetpersistant.etSubSpecialization.setText(name)
            if (!binding.layoutBottomsheetpersistant.etSubSpecialization.toString().isEmpty()){
                val listOfId= arrayOf(it.size)
                for (i in it.indices){
                    sub_SpecialistId.add(it[i].id)
                }

            }
        }

        sharedDataViewmodel.seniorityLevelData.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etSeniority.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etSeniority.toString().isEmpty()){
                SeniortyLevelId=it.id
            }else SeniortyLevelId=0
        }
        sharedDataViewmodel.getCity.observe(viewLifecycleOwner, Observer {
            binding.layoutBottomsheetpersistant.etChooseCity.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etChooseCity.toString().isEmpty()){
                CityId=it.id
            }else CityId=0
        })

        sharedDataViewmodel.getArea.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etChooseArea.setText(it.name)
            if (!binding.layoutBottomsheetpersistant.etChooseArea.toString().isEmpty()){
                AreaId=it.id
            }else AreaId=0
        }

        sharedDataViewmodel.chooseGender.observe(viewLifecycleOwner) {
            binding.layoutBottomsheetpersistant.etChooseGender.setText(it.gender)
            if (!binding.layoutBottomsheetpersistant.etChooseGender.toString().isEmpty()){
                GenderId=it.id
            }else GenderId=0

        }
    }

    private fun doctorsSearch(){

        partMap=partMap+ mapOf("MaxResultCount" to 10)
        partMap=partMap+ mapOf("SkipCount" to 0)
        partMap=partMap+ mapOf("MedicalExaminationTypeId" to 1)
        partMap=partMap+ mapOf("DoctorName" to "mostafa")
        partMap=partMap+ mapOf("FeesFrom" to 0)
        partMap=partMap+ mapOf("FeesTo" to 0)

        viewmodel.docorsResponse.observe(viewLifecycleOwner) {

            when(it){

                is Resource.Loading->{
                    showprogtessbar()
                }

                is Resource.sucess->{
                    hideprogressbar()
                    it.let {
                        bottomsheetbeahavoir.state=BottomSheetBehavior.STATE_HIDDEN
                        searchDoctorsAdapter.submitList(it.data)
                        searchDoctorsAdapter.notifyDataSetChanged()


                    }

                }

                is Resource.Error->{
                    hideprogressbar()
//                   loginresponse.data?.let {
//                       Log.e("msg : ",it.message)
//
//                   }
                }
            }

        }

        val doctorssearchRequset=DoctorSearchRequest(AreaId,CityId,"mostafa", feesFrom = 0
        , feesTo = 0,GenderId,10,1,SeniortyLevelId,0,SpecialistId,sub_SpecialistId)
        viewmodel.searchDoctors(doctorssearchRequset)
    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.layoutBottomsheetpersistant.progressBar.visibility=View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
        binding.layoutBottomsheetpersistant.progressBar.visibility=View.GONE

    }


}