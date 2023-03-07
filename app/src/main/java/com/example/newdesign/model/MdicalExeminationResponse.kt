package com.example.newdesign.model


import com.google.gson.annotations.SerializedName

data class MedicalExaminationResponse(
    @SerializedName("Data")
    val data: List<MedicalExamination?>?
)

data class MedicalExamination(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("Inactive")
    val inactive: Boolean?,
    @SerializedName("Name")
    val name: String?
)