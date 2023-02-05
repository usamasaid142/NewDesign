package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newdesign.model.*
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.docotorsearch.DoctorSearchResponseItem
import com.example.newdesign.model.register.*
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
import com.google.gson.Gson
import com.karumi.dexter.listener.PermissionGrantedResponse.from
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import javax.inject.Inject

@HiltViewModel
class DialogBottomSheetViewmodel @Inject constructor(private val repositry: RegisterRepositry):ViewModel(){


    val nationalityResponse=MutableLiveData<Resource<GetCountry>>()
    val specialistResponse=MutableLiveData<Resource<GetSpecialistResponse>>()
    val subSpecialistResponse=MutableLiveData<Resource<GetSubSpecilistResponse>>()
    val seniorityLevelResponse=MutableLiveData<Resource<GetSeniorityLevelResponse>>()
    val allCitiesResponse=MutableLiveData<Resource<GetAllCitiesResponse>>()
    val allAreasByCityIdResponse=MutableLiveData<Resource<GetAreasByCityIdResponse>>()
    val docorsResponse=MutableLiveData<Resource<List<DoctorSearchResponseItem>>>()



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


    fun getSubSpecialist(specialListId:Int)=viewModelScope.launch(Dispatchers.IO) {
        subSpecialistResponse.postValue(Resource.Loading())
        val response=repositry.getSubSpecialist(specialListId)
        subSpecialistResponse.postValue(handelGetSUBSpecialist(response))
    }

    private fun handelGetSUBSpecialist(response: Response<GetSubSpecilistResponse>): Resource<GetSubSpecilistResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


    fun getSeniorityLevel()=viewModelScope.launch(Dispatchers.IO) {
        seniorityLevelResponse.postValue(Resource.Loading())
        val response=repositry.getSeniorityLevel()
        seniorityLevelResponse.postValue(handelGetseniorityLevel(response))
    }

    private fun handelGetseniorityLevel(response: Response<GetSeniorityLevelResponse>): Resource<GetSeniorityLevelResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())

    }

    fun getAllCities()=viewModelScope.launch(Dispatchers.IO) {
        allCitiesResponse.postValue(Resource.Loading())
        val response=repositry.getAllCities()
        allCitiesResponse.postValue(handelGetAllCities(response))
    }

    private fun handelGetAllCities(response: Response<GetAllCitiesResponse>): Resource<GetAllCitiesResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())    }

    fun getAreasByCityId(cityId:Int)=viewModelScope.launch(Dispatchers.IO) {
        allAreasByCityIdResponse.postValue(Resource.Loading())
        val response=repositry.getAreasByCityId(cityId)
        allAreasByCityIdResponse.postValue(handelgetAreasByCityId(response))
    }

    private fun handelgetAreasByCityId(response: Response<GetAreasByCityIdResponse>): Resource<GetAreasByCityIdResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

     fun searchDoctors(doctorSearchRequest: DoctorSearchRequest)=viewModelScope.launch(Dispatchers.IO) {
        docorsResponse.postValue(Resource.Loading())
        val response=repositry.searchDoctors(doctorSearchRequest)
        docorsResponse.postValue(handlingSearchDoctors(response))
    }

    private fun handlingSearchDoctors(response: Response<List<DoctorSearchResponseItem>>): Resource<List<DoctorSearchResponseItem>>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())

    }

}