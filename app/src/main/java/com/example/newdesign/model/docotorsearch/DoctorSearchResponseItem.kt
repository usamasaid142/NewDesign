package com.example.newdesign.model.docotorsearch


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoctorSearchResponseItem(
    @SerializedName("AvailableFrom")
    val availableFrom: String?,
    @SerializedName("ClinicId")
    val clinicId: Int?,
    @SerializedName("ClinicName")
    val clinicName: String?,
    @SerializedName("DoctorId")
    val doctorId: Int?,
    @SerializedName("DoctorName")
    val doctorName: String?,
    @SerializedName("FeesFrom")
    val feesFrom: Int?,
    @SerializedName("FeesTo")
    val feesTo: Int?,
    @SerializedName("HealthEntities")
    val healthEntities: List<HealthEntity?>?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("MedicalExamationTypes")
    val medicalExamationTypes: List<MedicalExamationType?>?,
    @SerializedName("NumVisites")
    val numVisites: Int?,
    @SerializedName("Rate")
    val rate: Int?,
    @SerializedName("SeniortyLevelName")
    val seniortyLevelName: String?,
    @SerializedName("SpecialistName")
    val specialistName: String?,
    @SerializedName("SubSpecialistName")
    val subSpecialistName: List<String?>?,
    @SerializedName("SumRate")
    val sumRate: Int?,
    @SerializedName("WaitingTime")
    val waitingTime: Int?
): Serializable
data class HealthEntity(
    @SerializedName("Address")
    val address: String?,
    @SerializedName("ApartmentNo")
    val apartmentNo: String?,
    @SerializedName("AreaName")
    val areaName: String?,
    @SerializedName("BlockNo")
    val blockNo: String?,
    @SerializedName("CityName")
    val cityName: String?,
    @SerializedName("ClinicId")
    val clinicId: Int?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("FixedFee")
    val fixedFee: Int?,
    @SerializedName("FloorNo")
    val floorNo: Int?,
    @SerializedName("Logo")
    val logo: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Phone")
    val phone: String?
):Serializable

data class MedicalExamationType(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("Name")
    val name: String?
):Serializable