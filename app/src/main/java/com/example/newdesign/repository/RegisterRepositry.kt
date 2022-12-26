package com.example.newdesign.repository

import com.example.newdesign.api.ApiService
import com.example.newdesign.model.register.CreateUser
import javax.inject.Inject

class RegisterRepositry @Inject constructor(private val apiService: ApiService) {

    suspend fun createUser(culture:String,createUser: CreateUser)=apiService.createUser(culture,createUser)
}