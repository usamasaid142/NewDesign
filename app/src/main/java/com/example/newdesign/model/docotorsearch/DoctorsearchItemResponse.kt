package com.example.newdesign.model.docotorsearch


import com.google.gson.annotations.SerializedName

data class DoctorsearchItemResponse(
    @SerializedName("Data")
    val `data`: Data?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("MessageCode")
    val messageCode: Int?,
    @SerializedName("Success")
    val success: Boolean?
)