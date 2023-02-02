package com.example.newdesign.model.docotorsearch


import com.google.gson.annotations.SerializedName

data class DoctorSearchRequest(
    @SerializedName("AreaId")
    val areaId: Int?,
    @SerializedName("AvalibleDate")
    val avalibleDate: String?,
    @SerializedName("CityId")
    val cityId: Int?,
    @SerializedName("DoctorName")
    val doctorName: String?,
    @SerializedName("FeesFrom")
    val feesFrom: Int?,
    @SerializedName("FeesTo")
    val feesTo: Int?,
    @SerializedName("GenderId")
    val genderId: Int?,
    @SerializedName("MaxResultCount")
    val maxResultCount: Int?,
    @SerializedName("MedicalExaminationTypeId")
    val medicalExaminationTypeId: Int?,
    @SerializedName("SeniortyLevelId")
    val seniortyLevelId: Int?,
    @SerializedName("SkipCount")
    val skipCount: Int?,
    @SerializedName("SpecialistId")
    val specialistId: Int?,
    @SerializedName("SubSpecialistId")
    val subSpecialistId: List<Int?>?
)