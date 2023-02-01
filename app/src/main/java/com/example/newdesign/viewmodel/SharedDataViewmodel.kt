package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newdesign.model.SpecialistData
import dagger.hilt.android.lifecycle.HiltViewModel

class SharedDataViewmodel:ViewModel() {

    val specialication=MutableLiveData<SpecialistData>()

    fun getspecialication(specialist:SpecialistData){
        specialication.postValue(specialist)
    }
}