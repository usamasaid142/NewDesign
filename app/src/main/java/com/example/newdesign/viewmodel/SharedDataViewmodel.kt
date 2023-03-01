package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newdesign.adapter.GetAllAreaAdapter
import com.example.newdesign.model.*
import com.example.newdesign.model.booking.ClinicSchedualByClinicDayId
import com.example.newdesign.model.register.ChooseGender
import dagger.hilt.android.lifecycle.HiltViewModel

class SharedDataViewmodel:ViewModel() {

    val specialication=MutableLiveData<SpecialistData>()
    val subSpecialication=MutableLiveData<List<SubSpecialistData>>()
    val seniorityLevelData=MutableLiveData<SeniorityLevelData>()
    val getCity=MutableLiveData<CityData>()
    val getArea=MutableLiveData<AreaData>()
    val doctorId=MutableLiveData<Int>()
    val chooseGender=MutableLiveData<ChooseGender>()
    val ClinicSchedualByClinicDayId=MutableLiveData<ClinicSchedualByClinicDayId>()

    fun getspecialication(specialist:SpecialistData){
        specialication.postValue(specialist)
    }

    fun getSubSpecialication(subSpecialist:List<SubSpecialistData>){
        subSpecialication.postValue(subSpecialist)
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
}