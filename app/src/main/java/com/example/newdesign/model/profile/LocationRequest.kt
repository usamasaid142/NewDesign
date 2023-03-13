package com.example.newdesign.model.profile


import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("ApartmentNo")
    val apartmentNo: String?,
    @SerializedName("AreaId")
    val areaId: Int?,
    @SerializedName("BlockNo")
    val blockNo: String?,
    @SerializedName("CityId")
    val cityId: Int?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("EmergencyContact")
    val emergencyContact: String?,
    @SerializedName("FloorNo")
    val floorNo: Int?,
    @SerializedName("Address")
    val Address: String?,
)