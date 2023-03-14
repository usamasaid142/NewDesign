package com.example.newdesign.repository

import com.example.newdesign.api.ApiService
import com.example.newdesign.model.booking.BookingRequest
import com.example.newdesign.model.booking.PatientAppointmentRequest
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.profile.LocationRequest
import com.example.newdesign.model.register.*
import okhttp3.MultipartBody
import javax.inject.Inject

class RegisterRepositry @Inject constructor(private val apiService: ApiService) {

    suspend fun login(culture:String,loginUser: LoginUser)=apiService.Login(culture, loginUser)
    suspend fun registerUser(culture:String,registerUser: CreateUser)=apiService.registerUser(culture,registerUser)
    suspend fun SendOTP(culture:String,registerUser: CreateUser)=apiService.SendOTP(culture,registerUser)
    suspend fun createUser(culture:String,createUser: CreateUser)=apiService.createUser(culture,createUser)
    suspend fun resetPassword(resetRequest: ResetRequest)=apiService.resetPassword("En",resetRequest)
    suspend fun updatePassword(resetChangePassword: ResetChangePassword)=apiService.updatePassword("En",resetChangePassword)
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest)=apiService.changePassword("En",changePasswordRequest)
    suspend fun getHomeAds()=apiService.getHomeAds("En",1,true)
    suspend fun getCountries()=apiService.getCountries("En")
    suspend fun getSpecialist()=apiService.getSpecialist("En")
    suspend fun getMedicalExamination()=apiService.getMedicalExamination("En")
    suspend fun getSubSpecialist(specialListId:Int)=apiService.getSubSpecialist("En",specialListId)
    suspend fun getSeniorityLevel()=apiService.getSeniorityLevel("En")
    suspend fun getAllCities()=apiService.getAllCities("En")
    suspend fun getAreasByCityId(cityId:Int)=apiService.getAreasByCityId("En",cityId)
    suspend fun searchDoctors(doctorSearchRequest: DoctorSearchRequest)=apiService.searchDoctors("En",doctorSearchRequest)
    suspend fun getClinicSchedualByClinicDayId(ClinicId: Int,DayId:Int,
                                               MedicalExaminationTypeId:Int,
                                               BookDate:String
                                               )=apiService.getClinicSchedualByClinicDayId("en",ClinicId,DayId,MedicalExaminationTypeId,BookDate)

    suspend fun createPatientAppointment(appointmentRequest: PatientAppointmentRequest)=apiService.createPatientAppointment("En",appointmentRequest)
    suspend fun getDoctorProfileByDoctorId( DoctorId :Int)=apiService.getDoctorProfileByDoctorId("En",DoctorId)
    suspend fun getPatientAppointmentes(medicalExaminationTypeId:Int?)=apiService.getPatientAppointmentes("En",medicalExaminationTypeId)
    suspend fun cancelPatientAppointment(AppointmentId :Int)=apiService.cancelPatientAppointment("En",AppointmentId)
    suspend fun createPatientProfile(partmap:MultipartBody)=apiService.createPatientProfile("En",partmap)
    suspend fun sendPatientLocation(locationRequest: LocationRequest)=apiService.sendPatientLocation("En",locationRequest)

}