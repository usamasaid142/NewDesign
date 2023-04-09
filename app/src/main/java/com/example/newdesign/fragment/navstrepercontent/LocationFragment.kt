package com.example.newdesign.fragment.navstrepercontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.LocationfragmentBinding
import com.example.newdesign.model.profile.LocationRequest
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.Resource
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var binding: LocationfragmentBinding
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    var cityId=0
    var areaId=0
    @Inject
    lateinit var sp: SpUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= LocationfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        getdata()
    }


    private fun initButton(){

        binding.btnNext.setOnClickListener {

            if (isvalidateFeilds(binding.etChooseCity.text.toString(),
                    binding.etChooseArea.text.toString())){
                sendData()
            }
        }

        binding.layoutChooseCity.setOnClickListener {

            val action=LocationFragmentDirections.actionLocationFragment2ToDialogBottomSheetFragment("AllCity")
            findNavController().navigate(action)
        }

        binding.layoutChooseArea.setOnClickListener {
            if (!binding.etChooseCity.text.toString().isNullOrEmpty()){
                val action=LocationFragmentDirections.actionLocationFragment2ToDialogBottomSheetFragment("AllArea")
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(),"choose city first",Toast.LENGTH_SHORT).show()
            }

        }
    }



    private fun isvalidateFeilds(
        city: String,
       area: String,
    ): Boolean {

        var isValid=true

        if (city.trim().isNullOrEmpty()) {
            binding.etChooseCity.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.tvCityError.text = getString(R.string.required)
            binding.tvCityError.visibility = View.VISIBLE
            binding.layoutChooseCity.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false
        } else {
            binding.tvCityError.visibility = View.GONE
            binding.layoutChooseCity.setBackgroundResource(R.drawable.bg_edittext)
            binding.etChooseCity.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }

        if (area.trim().isEmpty()) {
            binding.tvAreaError.text = getString(R.string.required)
            binding.tvAreaError.visibility = View.VISIBLE
            binding.layoutChooseArea.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etChooseArea.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvAreaError.visibility = View.GONE
            binding.layoutChooseArea.setBackgroundResource(R.drawable.bg_edittext)
            binding.etChooseArea.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }


        return isValid

    }

    private fun getdata(){
        sharedDataViewmodel.getCity.observe(viewLifecycleOwner, Observer {
            cityId=it.id
            binding.etChooseCity.setText(it.name)
        })

        sharedDataViewmodel.getArea.observe(viewLifecycleOwner, Observer {
            binding.etChooseArea.setText(it.name)
            areaId=it.id
        })

    }

    private fun sendData(){

        var etFloorNumber=0
        if (!binding.etFloorNumber.text.toString().isNullOrEmpty()){
            etFloorNumber=binding.etFloorNumber.text.toString().toInt()

        }

        val locationRequest=LocationRequest("${binding.etApartmentNumber.text}"+"",areaId,"${binding.etBuildingNumber.text}"+"",
        cityId,1,"",etFloorNumber,"${binding.etStreet.text}"+".")
        viewmodel.sendPatientLocation(locationRequest)

        viewmodel.patientLocationResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.sucess -> {
                    binding.progressBar.visibility=View.GONE
                    response.data?.let {
                        it.data?.profileStatus?.let { it1 -> sharedDataViewmodel.getProfileStatus(it1) }
                        it.data?.profileStatus?.let { it1 ->
                            sp.saveProfileStatus(
                                Constans.PROFILE_STATUS,
                                it1
                            )
                        }
                        findNavController().navigate(R.id.medicalStateFragment)
                    }

                }
                is Resource.Error -> {
                    binding.progressBar.visibility=View.GONE
                    Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
            }


             }
    }

}