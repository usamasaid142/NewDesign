package com.example.newdesign.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetAllCitiesResponse(
    @SerializedName("Data")
    val `data`: List<CityData>
):Serializable

data class CityData(
    @SerializedName("CountryId")
    val countryId: Int,
    @SerializedName("CountryName")
    val countryName: String,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Inactive")
    val inactive: Boolean,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NameAr")
    val nameAr: String
):Serializable