package com.example.newdesign.model.register


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class RegisterReponse(
    @SerializedName("Data")
    val `data`: Data,
    @SerializedName("Message")
    val message: String,
    @SerializedName("MessageCode")
    val messageCode: Int,
    @SerializedName("Success")
    val success: Boolean
): Serializable

data class Data(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("ReSendCounter")
    val reSendCounter: Int,
    @SerializedName("UserId")
    val userId: Int
):Serializable