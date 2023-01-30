package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newdesign.model.GetSpecialistResponse
import com.example.newdesign.model.GetSubSpecilistResponse
import com.example.newdesign.model.register.*
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
import com.google.gson.Gson
import com.karumi.dexter.listener.PermissionGrantedResponse.from
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DialogBottomSheetViewmodel @Inject constructor(private val repositry: RegisterRepositry):ViewModel(){


    val nationalityResponse=MutableLiveData<Resource<GetCountry>>()
    val specialistResponse=MutableLiveData<Resource<GetSpecialistResponse>>()
    val subSpecialistResponse=MutableLiveData<Resource<GetSubSpecilistResponse>>()



    fun getSpecialist()=viewModelScope.launch(Dispatchers.IO) {
        specialistResponse.postValue(Resource.Loading())
        val response=repositry.getSpecialist()
        specialistResponse.postValue(handleGetSpecialist(response))
    }

    private fun handleGetSpecialist(response: Response<GetSpecialistResponse>): Resource<GetSpecialistResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())    }

    fun getCountries()=viewModelScope.launch(Dispatchers.IO) {
        nationalityResponse.postValue(Resource.Loading())
        val response=repositry.getCountries()
        nationalityResponse.postValue(handleGetCountries(response))
    }

    private fun handleGetCountries(response: Response<GetCountry>): Resource<GetCountry>? {
        if (response.isSuccessful){
            response.body()?.let {
               return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

//    fun getSubSpecialist(specialListId:Int)=viewModelScope.launch(Dispatchers.IO) {
//        subSpecialistResponse.postValue(Resource.Loading())
//        val response=repositry.getSubSpecialist(specialListId)
//        subSpecialistResponse.postValue(handelGetSUBSpecialist(response))
//    }
//
//    private fun handelGetSUBSpecialist(response: Response<GetSubSpecilistResponse>): Resource<GetSubSpecilistResponse>? {
//        if (response.isSuccessful){
//            response.body()?.let {
//                return Resource.sucess(it)
//            }
//        }
//        return Resource.Error(response.message())
//    }


}