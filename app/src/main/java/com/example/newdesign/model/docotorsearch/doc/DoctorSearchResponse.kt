package com.example.newdesign.model.docotorsearch.doc


import com.google.gson.annotations.SerializedName

data class DoctorSearchResponse(
    @SerializedName("Data")
    val `data`: Data?
)