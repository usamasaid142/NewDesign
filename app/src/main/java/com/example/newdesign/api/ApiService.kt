package com.example.newdesign.api

import com.example.newdesign.model.*
import com.example.newdesign.model.booking.*
import com.example.newdesign.model.booking.clink.GetDoctorClinksResponse
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.docotorsearch.DoctorsearchItemResponse
import com.example.newdesign.model.healthy.GetHealthEntityResponse
import com.example.newdesign.model.populardoctors.GetDoctorHealthTopicsResponse
import com.example.newdesign.model.populardoctors.GetDoctorSpotLightResponse
import com.example.newdesign.model.populardoctors.PopularDoctorsResponseItem
import com.example.newdesign.model.profile.LocationRequest
import com.example.newdesign.model.profile.PatientLocationResponse
import com.example.newdesign.model.profile.PatientProfileResponse
import com.example.newdesign.model.profile.UpdateProfilePatientResponse
import com.example.newdesign.model.register.*
import com.example.newdesign.model.scheduling.CancelPatientAppointmentResponse
import com.example.newdesign.model.scheduling.GetPatientAppointmentesResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("{culture}/User/Login")
    suspend fun Login(@Path("culture") culture: String,
                             @Body loginUser: LoginUser):Response<LoginResponse>
    @POST("{culture}/User/Register")
    suspend fun registerUser(@Path("culture") culture: String,
                           @Body registerUser: CreateUser):Response<RegisterReponse>

    @POST("{culture}/User/Register")
    suspend fun SendOTP(@Path("culture") culture: String,
                        @Body registerUser: CreateUser):Response<RegisterReponse>

    @POST("{culture}/User/CreateUser")
    suspend fun createUser(@Path("culture") culture: String,
                             @Body createUser: CreateUser):Response<CreateUserResponse>

    @GET("{culture}/LookUp/GetCountries")
    suspend fun getCountries(
        @Path("culture") culture: String,
    ): Response<GetCountry>

    @GET("{culture}/Ads/GetVidoesAds")
    suspend fun getHomeAds(
        @Path("culture") culture: String,
        @Query("PageID") PageID: Int,
        @Query("DoctorApp") DoctorApp: Boolean
    ): Response<HomeAdsResponse>

    @GET("{culture}/Specialist/GetSpecialist")
    suspend fun getSpecialist(
        @Path("culture") culture: String,
    ): Response<GetSpecialistResponse>

    @GET("{culture}/LookUp/GetMedicalExaminationType")
    suspend fun getMedicalExamination(
        @Path("culture") culture: String,
    ): Response<MedicalExaminationResponse>

    @GET("{culture}/Specialist/GetSubSpecialist")
    suspend fun getSubSpecialist(
        @Path("culture") culture: String,
        @Query("specialListId") specialListId:Int,
    ): Response<GetSubSpecilistResponse>

    @GET("{culture}/SeniorityLevel/GetSeniorityLevel")
    suspend fun getSeniorityLevel(
        @Path("culture") culture: String,
    ): Response<GetSeniorityLevelResponse>

    @GET("{culture}/City/GetAllCities")
    suspend fun getAllCities(
        @Path("culture") culture: String,
    ): Response<GetAllCitiesResponse>

    @GET("{culture}/Area/GetAreasByCityId")
    suspend fun getAreasByCityId(
        @Path("culture") culture: String,
        @Query("cityId") cityId:Int,
    ): Response<GetAreasByCityIdResponse>

    @POST("{culture}/DoctorSearch/DoctorSearch")
    suspend fun searchDoctors(
        @Path("culture") culture: String,
        @Body doctorSearchRequest:DoctorSearchRequest
    ): Response<DoctorsearchItemResponse>

    @GET("{culture}/DoctorClinic/GetClinicSchedualByClinicDayId")
    suspend fun getClinicSchedualByClinicDayId(
        @Path("culture") culture: String,
        @Query("ClinicId") ClinicId :Int,
        @Query("DayId") DayId  :Int,
        @Query("MedicalExaminationTypeId") MedicalExaminationTypeId :Int,
        @Query("BookDate") BookDate :String,
    ): Response<BookingResponse>

    @POST("{culture}/Patient/CreatePatientAppointment")
    suspend fun createPatientAppointment(
        @Path("culture") culture: String,
        @Body appointmentRequest: PatientAppointmentRequest
    ): Response<CreatepatientAppointementsResponse>

    @GET("{culture}/Doctor/GetDoctorProfileByDoctorId")
    suspend fun getDoctorProfileByDoctorId(
        @Path("culture") culture: String,
        @Query("DoctorId") DoctorId :Int,
    ): Response<GetDoctorClinksResponse>

    @GET("{culture}/PatientAppointment/GetPatientAppointmentes")
    suspend fun getPatientAppointmentes(
        @Path("culture") culture: String,
      @Query("medicalExaminationTypeId") medicalExaminationTypeId :Int?=null,
    ): Response<GetPatientAppointmentesResponse>

    @GET("{culture}/PatientAppointment/CancelPatientAppointment")
    suspend fun cancelPatientAppointment(
        @Path("culture") culture: String,
        @Query("AppointmentId") AppointmentId :Int,
    ): Response<CancelPatientAppointmentResponse>

    @POST("{culture}/Patient/CreatePatientProfileFirstStep")
    suspend fun createPatientProfile(
        @Path("culture") culture: String,
        @Body part: RequestBody
    ): Response<PatientProfileResponse>

    @POST("{culture}/Patient/updatePatientProfileFirstStep")
    suspend fun updatePatientProfile(
        @Path("culture") culture: String,
        @Body part: RequestBody
    ): Response<UpdateProfilePatientResponse>

    @POST("{culture}/Patient/CreateAndUpdatePatientProfileSecondStep")
    suspend fun sendPatientLocation(
        @Path("culture") culture: String,
        @Body locationRequest: LocationRequest
    ): Response<PatientLocationResponse>

    @POST("{culture}/User/ResetPassword")
    suspend fun resetPassword(
        @Path("culture") culture: String,
        @Body request: ResetRequest
    ): Response<ResetResponse>

    @POST("{culture}/User/UpdatePassword")
    suspend fun updatePassword(
        @Path("culture") culture: String,
        @Body resetChangePassword: ResetChangePassword
    ): Response<UpdatePasswordResponse>


    @POST("{culture}/User/ChangePassword")
    suspend fun changePassword(
        @Path("culture") culture: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

    @GET("{culture}/HealthEntity/GetHealthEntityPagedList")
    suspend fun getHealthEntityPagedList(
        @Path("culture") culture: String,
        @Query("CityId") CityId :Int,
        @Query("AreaId") AreaId :Int,
        @Query("HealthEntityTypeId") HealthEntityTypeId :Int,
        @Query("MaxResultCount") MaxResultCount :Int,
        @Query("SkipCount") SkipCount :Int,
    ): Response<GetHealthEntityResponse>

    @POST("{culture}/Doctor/GetPopularDoctors")
    suspend fun getPopularDoctors(
        @Path("culture") culture: String,
    ):Response<List<PopularDoctorsResponseItem>>

    @GET("{culture}/LookUp/GetDoctorSpotLight")
    suspend fun getDoctorSpotLight(
        @Path("culture") culture: String,
    ):Response<GetDoctorSpotLightResponse>

    @GET("{culture}/LookUp/GetDoctorHealthTopics")
    suspend fun getDoctorHealthTopics(
        @Path("culture") culture: String,
    ):Response<GetDoctorHealthTopicsResponse>


}