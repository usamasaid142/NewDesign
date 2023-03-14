package com.example.newdesign.model.register


import com.google.gson.annotations.SerializedName

data class ResetChangePassword(
    @SerializedName("Password")
    val password: String?,
    @SerializedName("UserId")
    val userId: Int?
)