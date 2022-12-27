package com.example.newdesign.model.register


import com.google.gson.annotations.SerializedName

data class CreateUserResponse(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Image")
    val image: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NameAR")
    val nameAR: String,
    @SerializedName("Phone")
    val phone: String,
    @SerializedName("ProfileStatus")
    val profileStatus: Int,
    @SerializedName("RoleID")
    val roleID: Int,
    @SerializedName("Token")
    val token: String
)