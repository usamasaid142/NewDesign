package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newdesign.model.register.CreateUser
import com.example.newdesign.model.register.RegisterReponse
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(private val repositry: RegisterRepositry):ViewModel(){


    val response=MutableLiveData<Resource<RegisterReponse>>()

    fun createUser(culture:String,createUser: CreateUser)=viewModelScope.launch {

        response.postValue(Resource.Loading())
        val registerResponse=repositry.createUser(culture,createUser)
        response.postValue(handleRegister(registerResponse))
    }

    private fun handleRegister(registerResponse: Response<RegisterReponse>): Resource<RegisterReponse>?{

        if (registerResponse.isSuccessful){
           registerResponse.body()?.let { registerResponse->
               return Resource.sucess(registerResponse)
           }

        }
            return Resource.Error(registerResponse.message())

    }


}