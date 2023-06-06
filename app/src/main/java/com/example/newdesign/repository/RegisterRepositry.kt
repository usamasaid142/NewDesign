package com.example.newdesign.repository

import com.example.newdesign.api.ApiService
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.profile.LocationRequest
import com.example.newdesign.model.register.*
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.SpUtil
import okhttp3.MultipartBody
import javax.inject.Inject

class RegisterRepositry @Inject constructor(private val apiService: ApiService) {
    @Inject
    lateinit var sp: SpUtil
    suspend fun login(culture:String,loginUser: LoginUser)=apiService.Login(culture, loginUser)
    suspend fun registerUser(culture:String,registerUser: CreateUser)=apiService.registerUser(culture,registerUser)
    suspend fun SendOTP(culture:String,registerUser: CreateUser)=apiService.SendOTP(culture,registerUser)
    suspend fun createUser(culture:String,createUser: CreateUser)=apiService.createUser(culture,createUser)
    suspend fun resetPassword(resetRequest: ResetRequest)=
        sp.getUserLang(Constans.Language)?.let { apiService.resetPassword(it,resetRequest) }
    suspend fun updatePassword(resetChangePassword: ResetChangePassword)=
        sp.getUserLang(Constans.Language)?.let { apiService.updatePassword(it,resetChangePassword) }
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest)=
        sp.getUserLang(Constans.Language)
            ?.let { apiService.changePassword(it,changePasswordRequest) }
    suspend fun getHomeAds()=
        sp.getUserLang(Constans.Language)?.let { apiService.getHomeAds(it,1,true) }
    suspend fun getCountries()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getCountries(it) }
    suspend fun getSpecialist()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getSpecialist(it) }
    suspend fun getMedicalExamination()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getMedicalExamination(it) }
    suspend fun getSubSpecialist(specialListId:Int)=
        sp.getUserLang(Constans.Language)?.let { apiService.getSubSpecialist(it,specialListId) }
    suspend fun getSeniorityLevel()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getSeniorityLevel(it) }
    suspend fun getAllCities()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getAllCities(it) }
    suspend fun getAreasByCityId(cityId:Int)=
        sp.getUserLang(Constans.Language)?.let { apiService.getAreasByCityId(it,cityId) }
    suspend fun searchDoctors(doctorSearchRequest: DoctorSearchRequest)=
        sp.getUserLang(Constans.Language)?.let { apiService.searchDoctors(it,doctorSearchRequest) }
    suspend fun getClinicSchedualByClinicDayId(ClinicId: Int,DayId:Int,
                                               MedicalExaminationTypeId:Int,
                                               BookDate:String
                                               )= sp.getUserLang(Constans.Language)
        ?.let { apiService.getClinicSchedualByClinicDayId(it,ClinicId,DayId,MedicalExaminationTypeId,BookDate) }

    suspend fun createPatientAppointment(appointmentRequest: PatientAppointmentRequest)=
        sp.getUserLang(Constans.Language)
            ?.let { apiService.createPatientAppointment(it,appointmentRequest) }
    suspend fun getDoctorProfileByDoctorId( DoctorId :Int)=
        sp.getUserLang(Constans.Language)
            ?.let { apiService.getDoctorProfileByDoctorId(it,DoctorId) }
    suspend fun getPatientAppointmentes(medicalExaminationTypeId:Int?)=
        sp.getUserLang(Constans.Language)
            ?.let { apiService.getPatientAppointmentes(it,medicalExaminationTypeId) }
    suspend fun cancelPatientAppointment(AppointmentId :Int)= sp.getUserLang(Constans.Language)
        ?.let { apiService.cancelPatientAppointment(it,AppointmentId) }
    suspend fun createPatientProfile(partmap:MultipartBody)=
        sp.getUserLang(Constans.Language)?.let { apiService.createPatientProfile(it,partmap) }

    suspend fun updatePatientProfile(partmap:MultipartBody)=
        sp.getUserLang(Constans.Language)?.let { apiService.updatePatientProfile(it,partmap) }

    suspend fun sendPatientLocation(locationRequest: LocationRequest)=
        sp.getUserLang(Constans.Language)
            ?.let { apiService.sendPatientLocation(it,locationRequest) }
    suspend fun getHealthEntityPagedList(CityId :Int,AreaId :Int,HealthEntityTypeId :Int,
                                         MaxResultCount :Int,SkipCount :Int)=
        sp.getUserLang(Constans.Language)?.let { apiService.getHealthEntityPagedList(it,CityId,AreaId,HealthEntityTypeId,MaxResultCount,SkipCount) }
    suspend fun getPopularDoctors()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getPopularDoctors(it) }
    suspend fun getDoctorHealthTopics()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getDoctorHealthTopics(it) }
    suspend fun getDoctorSpotLight()= sp.getUserLang(Constans.Language)
        ?.let { apiService.getDoctorSpotLight(it) }
}