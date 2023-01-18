package com.example.newdesign.repository

import com.example.newdesign.api.ApiService
import com.example.newdesign.model.register.CreateUser
import com.example.newdesign.model.register.LoginUser
import javax.inject.Inject

class RegisterRepositry @Inject constructor(private val apiService: ApiService) {

    suspend fun login(culture:String,loginUser: LoginUser)=apiService.Login(culture, loginUser)
    suspend fun registerUser(culture:String,registerUser: CreateUser)=apiService.registerUser(culture,registerUser)
    suspend fun SendOTP(culture:String,registerUser: CreateUser)=apiService.SendOTP(culture,registerUser)
    suspend fun createUser(culture:String,createUser: CreateUser)=apiService.createUser(culture,createUser)
}