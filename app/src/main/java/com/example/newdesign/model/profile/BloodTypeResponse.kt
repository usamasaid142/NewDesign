package com.example.newdesign.model.profile


import com.google.gson.annotations.SerializedName

data class BloodTypeResponse(
    @SerializedName("Data")
    val data: List<DataBloodType?>?,
    @SerializedName("Message")
    val message: String?
)

data class DataBloodType(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)