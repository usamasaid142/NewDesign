package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newdesign.adapter.GetAllAreaAdapter
import com.example.newdesign.model.*
import com.example.newdesign.model.booking.ClinicSchedualByClinicDayId
import com.example.newdesign.model.profile.DataBloodType
import com.example.newdesign.model.profile.DataFoodAllergy
import com.example.newdesign.model.profile.DataMedicineAllergy
import com.example.newdesign.model.register.ChooseGender
import com.example.newdesign.model.register.DataCountry
import dagger.hilt.android.lifecycle.HiltViewModel

class SharedDataViewmodel:ViewModel() {

    val specialication=MutableLiveData<SpecialistData>()
    val subSpecialication=MutableLiveData<List<SubSpecialistData>>()
    val seniorityLevelData=MutableLiveData<SeniorityLevelData>()
    val getCity=MutableLiveData<CityData>()
    val getArea=MutableLiveData<AreaData>()
    val doctorId=MutableLiveData<Int>()
    val profileStatus=MutableLiveData<Int>()
    val birthDate=MutableLiveData<String>()
    val chooseGender=MutableLiveData<ChooseGender>()
    val country=MutableLiveData<DataCountry>()
    val ClinicSchedualByClinicDayId=MutableLiveData<ClinicSchedualByClinicDayId>()
    val ExaminationTypeId=MutableLiveData<ImageServices>()
    val updateProfileStatus=MutableLiveData<Boolean>()
    val bloodTypesList= MutableLiveData<DataBloodType>()
    val medicineAllergyList=MutableLiveData<List<DataMedicineAllergy>>()
    val foodAllergyList=MutableLiveData<List<DataFoodAllergy>>()



    fun getspecialication(specialist:SpecialistData){
        specialication.postValue(specialist)
    }

    fun getSubSpecialication(subSpecialist:List<SubSpecialistData>){
        subSpecialication.postValue(subSpecialist)
    }
    fun getMedicineAllergy(medicineAllergy:List<DataMedicineAllergy>){
        medicineAllergyList.postValue(medicineAllergy)
    }
    fun getFoodAllergy(foodAllergy:List<DataFoodAllergy>){
        foodAllergyList.postValue(foodAllergy)
    }

    fun getBloodTypes(bloodTypes:DataBloodType){
        bloodTypesList.postValue(bloodTypes)
    }

    fun getSeniorityLevelData(seniorityLevel:SeniorityLevelData){
        seniorityLevelData.postValue(seniorityLevel)
    }

    fun getCity(city:CityData){
        getCity.postValue(city)
    }
    fun getArea(area:AreaData){
        getArea.postValue(area)
    }

    fun getGender(gender: ChooseGender){
        chooseGender.postValue(gender)
    }

    fun getClinicSchedualByClinicDayId(ClinicSchedual:ClinicSchedualByClinicDayId){
        ClinicSchedualByClinicDayId.postValue(ClinicSchedual)
    }

    fun getDocotorId(doctorid: Int){
        doctorId.postValue(doctorid)
    }

    fun getProfileStatus(profilestatus: Int){
        profileStatus.postValue(profilestatus)
    }
    fun getBirthDate(birthdate: String){
        this.birthDate.postValue(birthdate)
    }

    fun getCountry(selectCountry: DataCountry){
        country.postValue(selectCountry)
    }
    fun getMedicalExaminationTypeId(medicalExamination: ImageServices){
        ExaminationTypeId.postValue(medicalExamination)
    }

    fun getUpdateProfileStatus(status:Boolean){
        updateProfileStatus.postValue(status)

    }
}