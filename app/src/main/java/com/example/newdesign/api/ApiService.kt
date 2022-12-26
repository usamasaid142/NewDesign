package com.example.newdesign.api

import com.example.newdesign.model.register.CreateUser
import com.example.newdesign.model.register.RegisterReponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("{culture}/User/Register")
    suspend fun createUser(@Path("culture") culture: String,
                           @Body createUser: CreateUser):Response<RegisterReponse>


}