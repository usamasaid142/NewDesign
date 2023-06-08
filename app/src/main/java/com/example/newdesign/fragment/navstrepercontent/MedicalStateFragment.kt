package com.example.newdesign.fragment.navstrepercontent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.R
import com.example.newdesign.databinding.MedicalStatefragmentBinding
import com.example.newdesign.model.profile.*
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalStateFragment : Fragment() {

    private lateinit var binding: MedicalStatefragmentBinding
    private val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    private var bloodTypeId=0
    private var medicineAllergyListId = mutableListOf<Int>()
    private var foodAllergyListId = mutableListOf<Int>()
    var data: Map<String, Any> = HashMap()
    private var partMap: Map<String, Any> = mutableMapOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= MedicalStatefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        getData()
        viewmodel.getPatientMedicalInfo()
        callBackPatientMedicalInfo()
    }

    private fun  initButton(){
        binding.btnNext.setOnClickListener {
            sendPatientMedicalInfo()
        }

        binding.layoutBloodGroup.setOnClickListener {
            val action=MedicalStateFragmentDirections.actionMedicalStateFragment2ToDialogBottomSheetFragment("bloodTypes")
            findNavController().navigate(action)
        }

        binding.layoutMedicalAllergies.setOnClickListener {
            val action=MedicalStateFragmentDirections.actionMedicalStateFragment2ToDialogBottomSheetFragment("medicineAllergy")
            findNavController().navigate(action)
        }

        binding.layoutFoodAllergies.setOnClickListener {
            val action=MedicalStateFragmentDirections.actionMedicalStateFragment2ToDialogBottomSheetFragment("FoodAllergy")
            findNavController().navigate(action)
        }


    }

    private fun sendPatientMedicalInfo(){

        if (bloodTypeId!=null && bloodTypeId!=0){
            partMap = partMap + mapOf("BloodTypeId" to bloodTypeId)
        }
        if (!binding.etWeight.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("Weight" to binding.etWeight.text.toString().toInt())
        }
        if (!binding.etPressureMMGG.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("Pressure" to binding.etPressureMMGG.text.toString())
        }
        if (!binding.etSugarLevelMGDL.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("SugarLevel" to binding.etSugarLevelMGDL.text.toString())
        }
        if (!binding.etHight.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("Height" to binding.etHight.text.toString().toInt())
        }
        if (!binding.etOtherAllergies.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("OtherAllergies" to binding.etOtherAllergies.text.toString())
        }
        if (!binding.etPrescriptions.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("Prescriptions" to binding.etPrescriptions.text.toString())
        }
        if (!binding.etInjuries.text.toString().trim().isNullOrEmpty()){
            partMap = partMap + mapOf("Iinjuries" to binding.etInjuries.text.toString())
        }
        if (!foodAllergyListId.isNullOrEmpty()){
            partMap = partMap + mapOf("PatientFoodAllergiesDto" to foodAllergyListId)
        }
        if (!medicineAllergyListId.isNullOrEmpty()){
            partMap = partMap + mapOf("PatientMedicineAllergiesDto" to medicineAllergyListId)
        }

        viewmodel.createPatientMedicalInfo(partMap as HashMap<String, String>)
        viewmodel.patientMedicalInfoResponse.observe(viewLifecycleOwner, Observer {response->
            when (response) {
                is Resource.sucess -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.datasentsuccessfully),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }


                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    response.message?.let {
                        Snackbar.make(
                            requireView(),
                            "${response.message}",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

    }


    private fun getData(){
        sharedDataViewmodel.bloodTypesList.observe(viewLifecycleOwner) {
            binding.etBloodGroup.setText(it.name)
            bloodTypeId = if (binding.etBloodGroup.toString().isNotEmpty()) {
                it.id!!
            } else 0
        }

        sharedDataViewmodel.medicineAllergyList.observe(viewLifecycleOwner) {
            var name = ""
            for (i in it.indices) {

                name += "${it[i].name},"
            }
            binding.etMedicalAllergies.setText(name)
            if (!binding.etMedicalAllergies.toString().isEmpty()) {
                medicineAllergyListId.clear()
                for (i in it.indices) {
                    it[i].id?.let { it1 -> medicineAllergyListId.add(it1) }
                }

            }
        }

        sharedDataViewmodel.foodAllergyList.observe(viewLifecycleOwner) {
            var name = ""
            for (i in it.indices) {

                name += "${it[i].name},"
            }
            binding.etFoodAllergies.setText(name)
            if (!binding.etFoodAllergies.toString().isEmpty()) {
                foodAllergyListId.clear()
                for (i in it.indices) {
                    it[i].id?.let { it1 -> foodAllergyListId.add(it1) }
                }

            }
        }
    }

    private fun callBackPatientMedicalInfo(){

        viewmodel.patientMedicalInfo.observe(viewLifecycleOwner, Observer {response->
            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.data?.data?.let {

                        bindDataToViews(it)
                    }

                }
                is Resource.Error -> {
                    hideprogressbar()
                    response.data?.let {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

    }

    private fun showprogtessbar() {
        binding.progressBarInfo.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBarInfo.visibility = View.GONE
    }

    private fun bindDataToViews(patientInfo: DataPatientMedicalInfo) {

        if (patientInfo.height!=null){
            binding.etHight.setText(patientInfo.height.toString())
        }
        if (patientInfo.weight!=null)
        binding.etWeight.setText(patientInfo.weight.toString())
        if (patientInfo.bloodName!=null) {
            binding.etBloodGroup.setText(patientInfo.bloodName.toString())
        }else{
            binding.etBloodGroup.setText("")
        }
        if (patientInfo.pressure!=null){
            binding.etPressureMMGG.setText(patientInfo.pressure.toString())
        }else{
            binding.etPressureMMGG.setText("")
        }
        if (patientInfo.prescriptions!=null){
        binding.etPrescriptions.setText(patientInfo.prescriptions.toString())
        }else{
            binding.etPrescriptions.setText("")
        }
        if (patientInfo.sugarLevel!=null) {
            binding.etSugarLevelMGDL.setText(patientInfo.sugarLevel.toString())
        }else{
            binding.etSugarLevelMGDL.setText("")
        }
        if (patientInfo.surgeries!=null) {
            binding.etSurgeries.setText(patientInfo.surgeries.toString())
        }else{
            binding.etSurgeries.setText("")
        }
        if (patientInfo.currentMedication!=null) {
            binding.etCurrentMedication.setText(patientInfo.currentMedication.toString())
        }else{
            binding.etCurrentMedication.setText("")
        }
        if (patientInfo.pastMedication!=null){
            binding.etPastMedication.setText(patientInfo.pastMedication.toString())
        }else{
            binding.etPastMedication.setText("")
        }
        if (patientInfo.iinjuries!=null) {
            binding.etInjuries.setText(patientInfo.iinjuries.toString())
        }else{
            binding.etInjuries.setText("")
        }
        if (patientInfo.otherAllergies!=null){
            binding.etOtherAllergies.setText(patientInfo.otherAllergies.toString())
        }else{
            binding.etOtherAllergies.setText("")
        }

        if (!patientInfo.patientFoodAllergiesName.toString().isNullOrEmpty()){
            var name = ""
            for (i in patientInfo.patientFoodAllergiesName!!.indices) {

                name += "${patientInfo.patientFoodAllergiesName!![i]},"
            }
            binding.etFoodAllergies.setText(name)
        }else{
            binding.etFoodAllergies.setText("")
        }

        if (!patientInfo.patientMedicineAllergiesName.toString().isNullOrEmpty()){
            var medName = ""
            for (i in patientInfo.patientMedicineAllergiesName!!.indices) {

                medName += "${patientInfo.patientMedicineAllergiesName!![i]},"
            }
            binding.etMedicalAllergies.setText(medName)
        }else{
            binding.etMedicalAllergies.setText("")
        }


    }

}