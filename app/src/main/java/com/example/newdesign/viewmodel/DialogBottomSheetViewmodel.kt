package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newdesign.model.*
import com.example.newdesign.model.booking.BookingRequest
import com.example.newdesign.model.booking.BookingResponse
import com.example.newdesign.model.booking.CreatePatientAppointmentResponse
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.docotorsearch.DoctorsearchItemResponse
import com.example.newdesign.model.register.*
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
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
    val seniorityLevelResponse=MutableLiveData<Resource<GetSeniorityLevelResponse>>()
    val allCitiesResponse=MutableLiveData<Resource<GetAllCitiesResponse>>()
    val allAreasByCityIdResponse=MutableLiveData<Resource<GetAreasByCityIdResponse>>()
    val docorsResponse=MutableLiveData<Resource<DoctorsearchItemResponse>>()
    val medicalExamination=MutableLiveData<Resource<MedicalExaminationResponse>>()
    val bookingResponse=MutableLiveData<Resource<BookingResponse>>()
    val patientAppointmentResponse=MutableLiveData<Resource<CreatePatientAppointmentResponse>>()




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

    private fun handlingSearchDoctors(response: Response<DoctorsearchItemResponse>): Resource<DoctorsearchItemResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())

    }

    fun getMedicalExaminationType()=viewModelScope.launch(Dispatchers.IO) {
        medicalExamination.postValue(Resource.Loading())
        val response=repositry.getMedicalExamination()
        medicalExamination.postValue(handleGetMedicalExamination(response))
    }

    private fun handleGetMedicalExamination(response: Response<MedicalExaminationResponse>): Resource<MedicalExaminationResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


    fun getClinicSchedualByClinicDayId(ClinicId: Int,DayId:Int,
                                       MedicalExaminationTypeId:Int,
                                       BookDate:String)=viewModelScope.launch(Dispatchers.IO) {
        bookingResponse.postValue(Resource.Loading())
        val response=repositry.getClinicSchedualByClinicDayId(ClinicId,DayId,MedicalExaminationTypeId,BookDate)
        bookingResponse.postValue(handleGetClinicSchedualByClinicDayId(response))
    }

    private fun handleGetClinicSchedualByClinicDayId(response: Response<BookingResponse>): Resource<BookingResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


    fun createPatientAppointment(appointmentRequest: PatientAppointmentRequest)=viewModelScope.launch(Dispatchers.IO) {
        patientAppointmentResponse.postValue(Resource.Loading())
        val response=repositry.createPatientAppointment(appointmentRequest)
        patientAppointmentResponse.postValue(handlecreatePatientAppointment(response))
    }

    private fun handlecreatePatientAppointment(response: Response<CreatePatientAppointmentResponse>): Resource<CreatePatientAppointmentResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


}