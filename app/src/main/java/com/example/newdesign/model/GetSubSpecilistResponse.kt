package com.example.newdesign.model


import com.google.gson.annotations.SerializedName

data class GetSubSpecilistResponse(
    @SerializedName("Data")
    val `data`: List<SubSpecialistData>
)

data class SubSpecialistData(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Image")
    val image: Any,
    @SerializedName("inactive")
    val inactive: Boolean,
    @SerializedName("Name")
    val name: String
)