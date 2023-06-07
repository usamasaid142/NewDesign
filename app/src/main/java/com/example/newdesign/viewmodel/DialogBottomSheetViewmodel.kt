package com.example.newdesign.viewmodel

import android.util.Log
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
import com.example.newdesign.model.populardoctors.GetDoctorSpotLightResponse
import com.example.newdesign.model.populardoctors.PopularDoctorsResponseItem
import com.example.newdesign.model.profile.*
import com.example.newdesign.model.register.*
import com.example.newdesign.model.scheduling.CancelPatientAppointmentResponse
import com.example.newdesign.model.scheduling.GetPatientAppointmentesResponse
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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
    val doctorSpotLightResponse=MutableLiveData<Resource<GetDoctorSpotLightResponse>>()
    val updatePatientProfileResponse=MutableLiveData<Resource<UpdateProfilePatientResponse>>()
    val patientMedicalInfoResponse=MutableLiveData<Resource<PatientMedicalInfoResponse>>()
    val bloodTypesResponse=MutableLiveData<Resource<BloodTypeResponse>>()
    val medicineAllergyResponse=MutableLiveData<Resource<MedicineAllergyResponse>>()
    val foodAllergyResponse=MutableLiveData<Resource<FoodAllergyResponse>>()




    fun getSpecialist()=viewModelScope.launch(Dispatchers.IO+handler) {
        specialistResponse.postValue(Resource.Loading())
        val response=repositry.getSpecialist()
        specialistResponse.postValue(response?.let { handleGetSpecialist(it) })
    }

    private fun handleGetSpecialist(response: Response<GetSpecialistResponse>): Resource<GetSpecialistResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())    }

    fun getCountries()=viewModelScope.launch(Dispatchers.IO+handler) {
        nationalityResponse.postValue(Resource.Loading())
        val response=repositry.getCountries()
        nationalityResponse.postValue(response?.let { handleGetCountries(it) })
    }

    private fun handleGetCountries(response: Response<GetCountry>): Resource<GetCountry>? {
        if (response.isSuccessful){
            response.body()?.let {
               return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


    fun getSubSpecialist(specialListId:Int)=viewModelScope.launch(Dispatchers.IO+handler) {
        subSpecialistResponse.postValue(Resource.Loading())
        val response=repositry.getSubSpecialist(specialListId)
        subSpecialistResponse.postValue(response?.let { handelGetSUBSpecialist(it) })
    }

    private fun handelGetSUBSpecialist(response: Response<GetSubSpecilistResponse>): Resource<GetSubSpecilistResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getBloodTypes()=viewModelScope.launch(Dispatchers.IO+handler) {
        bloodTypesResponse.postValue(Resource.Loading())
        val response=repositry.getBloodTypes()
        bloodTypesResponse.postValue(response?.let { handelgetBloodTypes(it) })
    }

    private fun handelgetBloodTypes(response: Response<BloodTypeResponse>): Resource<BloodTypeResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<BloodTypeResponse>(response.errorBody()!!.string(), BloodTypeResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }

    fun getMedicineAllergy()=viewModelScope.launch(Dispatchers.IO+handler) {
        medicineAllergyResponse.postValue(Resource.Loading())
        val response=repositry.getMedicineAllergy()
        medicineAllergyResponse.postValue(response?.let { handelgetMedicineAllergys(it) })
    }
    private fun handelgetMedicineAllergys(response: Response<MedicineAllergyResponse>): Resource<MedicineAllergyResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<MedicineAllergyResponse>(response.errorBody()!!.string(), MedicineAllergyResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }

    fun getFoodAllergy()=viewModelScope.launch(Dispatchers.IO+handler) {
        foodAllergyResponse.postValue(Resource.Loading())
        val response=repositry.getFoodAllergy()
        foodAllergyResponse.postValue(response?.let { handelGetFoodAllergy(it) })
    }
    private fun handelGetFoodAllergy(response: Response<FoodAllergyResponse>): Resource<FoodAllergyResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<FoodAllergyResponse>(response.errorBody()!!.string(), FoodAllergyResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }


    fun getSeniorityLevel()=viewModelScope.launch(Dispatchers.IO+handler) {
        seniorityLevelResponse.postValue(Resource.Loading())
        val response=repositry.getSeniorityLevel()
        seniorityLevelResponse.postValue(response?.let { handelGetseniorityLevel(it) })
    }

    private fun handelGetseniorityLevel(response: Response<GetSeniorityLevelResponse>): Resource<GetSeniorityLevelResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())

    }

    fun getAllCities()=viewModelScope.launch(Dispatchers.IO+handler) {
        allCitiesResponse.postValue(Resource.Loading())
        val response=repositry.getAllCities()
        allCitiesResponse.postValue(response?.let { handelGetAllCities(it) })
    }

    private fun handelGetAllCities(response: Response<GetAllCitiesResponse>): Resource<GetAllCitiesResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())    }

    fun getAreasByCityId(cityId:Int)=viewModelScope.launch(Dispatchers.IO+handler) {
        allAreasByCityIdResponse.postValue(Resource.Loading())
        val response=repositry.getAreasByCityId(cityId)
        allAreasByCityIdResponse.postValue(response?.let { handelgetAreasByCityId(it) })
    }

    private fun handelgetAreasByCityId(response: Response<GetAreasByCityIdResponse>): Resource<GetAreasByCityIdResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

     fun searchDoctors(doctorSearchRequest: DoctorSearchRequest)=viewModelScope.launch(Dispatchers.IO+handler) {
        docorsResponse.postValue(Resource.Loading())
        val response=repositry.searchDoctors(doctorSearchRequest)
        docorsResponse.postValue(response?.let { handlingSearchDoctors(it) })
    }

    private fun handlingSearchDoctors(response: Response<DoctorsearchItemResponse>): Resource<DoctorsearchItemResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())

    }

    fun getMedicalExaminationType()=viewModelScope.launch(Dispatchers.IO+handler) {
        medicalExamination.postValue(Resource.Loading())
        val response=repositry.getMedicalExamination()
        medicalExamination.postValue(response?.let { handleGetMedicalExamination(it) })
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
                                       BookDate:String)=viewModelScope.launch(Dispatchers.IO+handler) {
        bookingResponse.postValue(Resource.Loading())
        val response=repositry.getClinicSchedualByClinicDayId(ClinicId,DayId,MedicalExaminationTypeId,BookDate)
        bookingResponse.postValue(response?.let { handleGetClinicSchedualByClinicDayId(it) })
    }

    private fun handleGetClinicSchedualByClinicDayId(response: Response<BookingResponse>): Resource<BookingResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }


    fun createPatientAppointment(appointmentRequest: PatientAppointmentRequest)=viewModelScope.launch(Dispatchers.IO+handler) {
        patientAppointmentResponse.postValue(Resource.Loading())
        val response=repositry.createPatientAppointment(appointmentRequest)
        patientAppointmentResponse.postValue(response?.let { handlecreatePatientAppointment(it) })
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

    fun getDoctorProfileByDoctorId(DoctorId :Int)=viewModelScope.launch(Dispatchers.IO+handler) {
        doctorClinkResponse.postValue(Resource.Loading())
        val response=repositry.getDoctorProfileByDoctorId(DoctorId)
        doctorClinkResponse.postValue(response?.let { handleGetDoctorProfileByDoctorId(it) })
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

    fun getPatientAppointmentes(medicalExaminationTypeId :Int?)=viewModelScope.launch(Dispatchers.IO+handler) {
        patientsAppointmentesResponse.postValue(Resource.Loading())
        val response=repositry.getPatientAppointmentes(medicalExaminationTypeId)
        patientsAppointmentesResponse.postValue(response?.let { handleGetPatientAppointmentes(it) })
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

    fun cancelPatientAppointment(AppointmentId :Int)=viewModelScope.launch(Dispatchers.IO+handler) {
        cancelPatientResponse.postValue(Resource.Loading())
        val response=repositry.cancelPatientAppointment(AppointmentId)
        cancelPatientResponse.postValue(response?.let { handleCancelPatientAppointment(it) })
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
    fun createPatientProfile(partmap: MultipartBody)= viewModelScope.launch(Dispatchers.IO+handler) {
        patientProfileResponse.postValue(Resource.Loading())
        val response = repositry.createPatientProfile(partmap)

        patientProfileResponse.postValue(response?.let { handlePatientProfile(it) })
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

    fun updatePatientProfile(partmap: MultipartBody)= viewModelScope.launch(Dispatchers.IO+handler) {
        updatePatientProfileResponse.postValue(Resource.Loading())
        val response = repositry.updatePatientProfile(partmap)

        updatePatientProfileResponse.postValue(response?.let { handleupdatePatientProfile(it) })
    }

    private fun handleupdatePatientProfile(response: Response<UpdateProfilePatientResponse>): Resource<UpdateProfilePatientResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<UpdateProfilePatientResponse>(response.errorBody()!!.string(), UpdateProfilePatientResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }


    fun sendPatientLocation(locationRequest: LocationRequest)= viewModelScope.launch(Dispatchers.IO+handler) {
        patientLocationResponse.postValue(Resource.Loading())
        val response = repositry.sendPatientLocation(locationRequest)
        patientLocationResponse.postValue(response?.let { handlePatientLocation(it) })
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

        healthyResponse.postValue(response?.let { handleGetHealthEntityPagedList(it) })
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

    fun getPopularDoctors()=viewModelScope.launch(Dispatchers.IO+handler) {
        popularResponse.postValue(Resource.Loading())
        val response=repositry.getPopularDoctors()
        popularResponse.postValue(response?.let { handlegetPopularDoctors(it) })
    }

    private fun handlegetPopularDoctors(response: Response<List<PopularDoctorsResponseItem>>): Resource<List<PopularDoctorsResponseItem>>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getDoctorHealthTopics()=viewModelScope.launch(Dispatchers.IO+handler) {
        healthTopicsResponse.postValue(Resource.Loading())
        val response=repositry.getDoctorHealthTopics()
        healthTopicsResponse.postValue(response?.let { handlegetDoctorHealthTopics(it) })
    }

    private fun handlegetDoctorHealthTopics(response: Response<GetDoctorHealthTopicsResponse>): Resource<GetDoctorHealthTopicsResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getDoctorSpotLight()=viewModelScope.launch(Dispatchers.IO+handler) {
        doctorSpotLightResponse.postValue(Resource.Loading())
        val response=repositry.getDoctorSpotLight()
        doctorSpotLightResponse.postValue(response?.let { handleGetDoctorSpotLight(it) })
    }

    private fun handleGetDoctorSpotLight(response: Response<GetDoctorSpotLightResponse>): Resource<GetDoctorSpotLightResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun createPatientMedicalInfo(medicalInfoRequest: HashMap<String,String>)=viewModelScope.launch(Dispatchers.IO+handler) {
        patientMedicalInfoResponse.postValue(Resource.Loading())
        val response=repositry.CreatePatientMedicallInfo(medicalInfoRequest)
        patientMedicalInfoResponse.postValue(response?.let { handlecreatePatientMedicallInfo(it) })
    }

    private fun handlecreatePatientMedicallInfo(response: Response<PatientMedicalInfoResponse>): Resource<PatientMedicalInfoResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        val error = Gson().fromJson<PatientMedicalInfoResponse>(response.errorBody()!!.string(), PatientMedicalInfoResponse::class.java)
        return error.message?.let { Resource.Error(it) }
    }


    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("exception","osama${exception.message.toString()}")
    }
}