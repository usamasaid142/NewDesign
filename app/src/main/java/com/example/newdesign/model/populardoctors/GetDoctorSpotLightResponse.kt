package com.example.newdesign.model.populardoctors


import com.google.gson.annotations.SerializedName

data class GetDoctorSpotLightResponse(
    @SerializedName("Data")
    val data: List<Data?>?,
    @SerializedName("Message")
    val message: String?
)

data class Data(
    @SerializedName("CreatedDate")
    val createdDate: String?,
    @SerializedName("Details")
    val details: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("ImageURL")
    val imageURL: String?,
    @SerializedName("IsHighlight")
    val isHighlight: Boolean?,
    @SerializedName("Title")
    val title: String?
)