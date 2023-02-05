package com.example.newdesign.api

import com.example.newdesign.model.*
import com.example.newdesign.model.docotorsearch.DoctorSearchRequest
import com.example.newdesign.model.docotorsearch.DoctorSearchResponseItem
import com.example.newdesign.model.register.*
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
    ): Response<List<DoctorSearchResponseItem>>

}