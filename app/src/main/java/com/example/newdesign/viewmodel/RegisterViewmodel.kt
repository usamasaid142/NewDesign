package com.example.newdesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newdesign.model.HomeAdsResponse
import com.example.newdesign.model.register.*
import com.example.newdesign.repository.RegisterRepositry
import com.example.newdesign.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(private val repositry: RegisterRepositry):ViewModel(){


    val response=MutableLiveData<Resource<RegisterReponse>>()
    val otpResponse=MutableLiveData<Resource<RegisterReponse>>()
    val createResponse=MutableLiveData<Resource<CreateUserResponse>>()
    val loginResponse=MutableLiveData<Resource<LoginResponse>>()
    val imagevedioresponse=MutableLiveData<Resource<HomeAdsResponse>>()

    fun registerUser(culture:String,createUser: CreateUser)=viewModelScope.launch(Dispatchers.IO)  {

        response.postValue(Resource.Loading())
        val registerResponse=repositry.registerUser(culture,createUser)
        response.postValue(handleRegister(registerResponse))
    }


    fun loginUser(culture:String,loginUser: LoginUser)=viewModelScope.launch(Dispatchers.IO) {
        loginResponse.postValue(Resource.Loading())
        val response=repositry.login(culture,loginUser)
        loginResponse.postValue(handleLogin(response))
    }

    private fun handleLogin(response: Response<LoginResponse>): Resource<LoginResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
               return Resource.sucess(it)
            }
        }
                val error = Gson().fromJson<LoginResponse>(response.errorBody()!!.string(), LoginResponse::class.java)
                return Resource.Error(error.message)


    }

    fun Sentotp(culture:String,registerUser: CreateUser)=viewModelScope.launch(Dispatchers.IO) {

        otpResponse.postValue(Resource.Loading())
        val response=repositry.SendOTP(culture,registerUser)
        otpResponse.postValue(handleOTP(response))
    }

    fun createuser(culture:String,createUser: CreateUser)=viewModelScope.launch(Dispatchers.IO)  {

        createResponse.postValue(Resource.Loading())
        val response=repositry.createUser(culture,createUser)
        createResponse.postValue(handleCreateUser(response))
    }

    private fun handleCreateUser(response: Response<CreateUserResponse>): Resource<CreateUserResponse>? {
        if (response.isSuccessful){
            response.body()?.let { registerResponse->
                return Resource.sucess(registerResponse)
            }

        }
        return Resource.Error(response.message())

    }

    private fun handleRegister(response: Response<RegisterReponse>): Resource<RegisterReponse>?{

        if (response.isSuccessful){
            response.body()?.let { registerResponse ->
                return Resource.sucess(registerResponse)
            }

        }

        return Resource.Error(response.message())

    }

    private fun handleOTP(otpResponse: Response<RegisterReponse>): Resource<RegisterReponse>?{

        if (otpResponse.isSuccessful){
            otpResponse.body()?.let { registerResponse->
                return Resource.sucess(registerResponse)
            }

        }
        return Resource.Error(otpResponse.message())

    }


    fun getHomeAdds()=viewModelScope.launch(Dispatchers.IO)  {

        imagevedioresponse.postValue(Resource.Loading())
        val imageVedioResponse=repositry.getHomeAds()
        imagevedioresponse.postValue(handleImageVedio(imageVedioResponse))
    }

    private fun handleImageVedio(imageVedioResponse: Response<HomeAdsResponse>): Resource<HomeAdsResponse>? {
        if (imageVedioResponse.isSuccessful){
            imageVedioResponse.body()?.let { it->
                return Resource.sucess(it)
            }
        }
        return Resource.Error(imageVedioResponse.message())
    }

}