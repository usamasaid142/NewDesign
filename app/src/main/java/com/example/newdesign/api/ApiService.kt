package com.example.newdesign.api

import com.example.newdesign.model.GetSeniorityLevelResponse
import com.example.newdesign.model.GetSpecialistResponse
import com.example.newdesign.model.GetSubSpecilistResponse
import com.example.newdesign.model.HomeAdsResponse
import com.example.newdesign.model.register.*
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

}