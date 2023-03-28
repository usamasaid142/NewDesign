package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newdesign.model.*
import com.example.newdesign.model.booking.*
import com.example.newdesign.model.booking.clink.GetDoctorClinksResponse
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.docotorsearch.DoctorsearchItemResponse
import com.example.newdesign.model.healthy.GetHealthEntityResponse
import com.example.newdesign.model.populardoctors.GetDoctorHealthTopicsResponse
import com.example.newdesign.model.populardoctors.PopularDoctorsResponseItem
import com.example.newdesign.model.profile.LocationRequest
import com.example.newdesign.model.profile.PatientLocationResponse
import com.example.newdesign.model.profile.PatientProfileResponse
import com.example.newdesign.model.register.*
import com.example.newdesign.model.scheduling.CancelPatientAppointmentResponse
import com.example.newdesign.model.scheduling.GetPatientAppointmentesResponse
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
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
    val patientAppointmentResponse=MutableLiveData<Resource<CreatepatientAppointementsResponse>>()
    val doctorClinkResponse=MutableLiveData<Resource<GetDoctorClinksResponse>>()
    val patientsAppointmentesResponse=MutableLiveData<Resource<GetPatientAppointmentesResponse>>()
    val cancelPatientResponse=MutableLiveData<Resource<CancelPatientAppointmentResponse>>()
    val patientProfileResponse=MutableLiveData<Resource<PatientProfileResponse>>()
    val patientLocationResponse=MutableLiveData<Resource<PatientLocationResponse>>()
    val healthyResponse=MutableLiveData<Resource<GetHealthEntityResponse>>()
    val popularResponse=MutableLiveData<Resource<List<PopularDoctorsResponseItem>>>()
    val healthTopicsResponse=MutableLiveData<Resource<GetDoctorHealthTopicsResponse>>()





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

    private fun handlecreatePatientAppointment(response: Response<CreatepatientAppointementsResponse>): Resource<CreatepatientAppointementsResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<CreatepatientAppointementsResponse>(response.errorBody()!!.string(), CreatepatientAppointementsResponse::class.java)
        return Resource.Error(error.message)
    }

    fun getDoctorProfileByDoctorId(DoctorId :Int)=viewModelScope.launch(Dispatchers.IO) {
        doctorClinkResponse.postValue(Resource.Loading())
        val response=repositry.getDoctorProfileByDoctorId(DoctorId)
        doctorClinkResponse.postValue(handleGetDoctorProfileByDoctorId(response))
    }

    private fun handleGetDoctorProfileByDoctorId(response: Response<GetDoctorClinksResponse>): Resource<GetDoctorClinksResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<GetDoctorClinksResponse>(response.errorBody()!!.string(), GetDoctorClinksResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }

    fun getPatientAppointmentes(medicalExaminationTypeId :Int?)=viewModelScope.launch(Dispatchers.IO) {
        patientsAppointmentesResponse.postValue(Resource.Loading())
        val response=repositry.getPatientAppointmentes(medicalExaminationTypeId)
        patientsAppointmentesResponse.postValue(handleGetPatientAppointmentes(response))
    }

    private fun handleGetPatientAppointmentes(response: Response<GetPatientAppointmentesResponse>): Resource<GetPatientAppointmentesResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<GetPatientAppointmentesResponse>(response.errorBody()!!.string(), GetPatientAppointmentesResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }

    fun cancelPatientAppointment(AppointmentId :Int)=viewModelScope.launch(Dispatchers.IO) {
        cancelPatientResponse.postValue(Resource.Loading())
        val response=repositry.cancelPatientAppointment(AppointmentId)
        cancelPatientResponse.postValue(handleCancelPatientAppointment(response))
    }

    private fun handleCancelPatientAppointment(response: Response<CancelPatientAppointmentResponse>): Resource<CancelPatientAppointmentResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<CancelPatientAppointmentResponse>(response.errorBody()!!.string(), CancelPatientAppointmentResponse::class.java)
        return error.message?.let { Resource.Error(it) }

    }
    fun createPatientProfile(partmap: MultipartBody)= viewModelScope.launch(Dispatchers.IO) {
        patientProfileResponse.postValue(Resource.Loading())
        val response = repositry.createPatientProfile(partmap)

        patientProfileResponse.postValue(handlePatientProfile(response))
    }

    private fun handlePatientProfile(response: Response<PatientProfileResponse>): Resource<PatientProfileResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<PatientProfileResponse>(response.errorBody()!!.string(), PatientProfileResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }


    fun sendPatientLocation(locationRequest: LocationRequest)= viewModelScope.launch(Dispatchers.IO) {
        patientLocationResponse.postValue(Resource.Loading())
        val response = repositry.sendPatientLocation(locationRequest)

        patientLocationResponse.postValue(handlePatientLocation(response))
    }

    private fun handlePatientLocation(response: Response<PatientLocationResponse>): Resource<PatientLocationResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<PatientLocationResponse>(response.errorBody()!!.string(), PatientLocationResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }

     fun getHealthEntityPagedList(CityId :Int,AreaId :Int,HealthEntityTypeId :Int,
                                         MaxResultCount :Int,SkipCount :Int)=viewModelScope.launch {
        healthyResponse.postValue(Resource.Loading())
        val response = repositry.getHealthEntityPagedList(CityId,AreaId,HealthEntityTypeId,MaxResultCount,SkipCount)

        healthyResponse.postValue(handleGetHealthEntityPagedList(response))
    }

    private fun handleGetHealthEntityPagedList(response: Response<GetHealthEntityResponse>): Resource<GetHealthEntityResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<GetHealthEntityResponse>(response.errorBody()!!.string(), GetHealthEntityResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }

    fun getPopularDoctors()=viewModelScope.launch(Dispatchers.IO) {
        popularResponse.postValue(Resource.Loading())
        val response=repositry.getPopularDoctors()
        popularResponse.postValue(handlegetPopularDoctors(response))
    }

    private fun handlegetPopularDoctors(response: Response<List<PopularDoctorsResponseItem>>): Resource<List<PopularDoctorsResponseItem>>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getDoctorHealthTopics()=viewModelScope.launch(Dispatchers.IO) {
        healthTopicsResponse.postValue(Resource.Loading())
        val response=repositry.getDoctorHealthTopics()
        healthTopicsResponse.postValue(handlegetDoctorHealthTopics(response))
    }

    private fun handlegetDoctorHealthTopics(response: Response<GetDoctorHealthTopicsResponse>): Resource<GetDoctorHealthTopicsResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


}